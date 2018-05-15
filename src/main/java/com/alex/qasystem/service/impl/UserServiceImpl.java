package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.*;
import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegistrationExecution;
import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AuthToken;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserAuthStateEnum;
import com.alex.qasystem.enums.UserRegistrationStateEnum;
import com.alex.qasystem.service.UserService;
import com.alex.qasystem.util.SecurityUtil;
import com.alex.qasystem.util.ValidationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * token 有效时间 两个小时
     */
    private final static long TOKEN_DURATION = 7200000;
    private UserMapper userMapper;
    private AuthTokenMapper authTokenMapper;
    private QuestionCommentMapper questionCommentMapper;
    private QuestionMapper questionMapper;
    private AnswerMapper answerMapper;
    private AnswerCommentMapper answerCommentMapper;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setQuestionCommentMapper(QuestionCommentMapper questionCommentMapper) {
        this.questionCommentMapper = questionCommentMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Autowired
    public void setAnswerCommentMapper(AnswerCommentMapper answerCommentMapper) {
        this.answerCommentMapper = answerCommentMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Autowired
    public void setAuthTokenMapper(AuthTokenMapper authTokenMapper) {
        this.authTokenMapper = authTokenMapper;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return userMapper.selectByEmail(email);
    }

    @Override
    public UserAuthExecution login(String email, String password) {

        // 登录信息不合法
        if (email == null || password == null) {
            return new UserAuthExecution(UserAuthStateEnum.INVALID_LOGIN_PARAMETERS);
        }

        User user = userMapper.selectByEmail(email);

        // 用户不存在, 邮箱尚未注册
        if (user == null) {
            return new UserAuthExecution(UserAuthStateEnum.USER_NOT_EXISTS);
        }

        // 邮箱密码不匹配
        if (!SecurityUtil.checkpw(password, user.getPassword())) {
            return new UserAuthExecution(UserAuthStateEnum.WRONG_PASSWORD);
        }

        // 已经登录
        AuthToken authToken = authTokenMapper.selectByUserId(user.getId());
        if (authToken != null) {
            UserAuthExecution userAuthExecution = new UserAuthExecution(UserAuthStateEnum.SUCCESS);
            userAuthExecution.setUser(user);
            userAuthExecution.setToken(authToken.getToken());
            return userAuthExecution;
        }

        // 验证成功
        else {
            // 为验证通过的用户设置token
            authToken = new AuthToken();
            authToken.setUserId(user.getId());
            authToken.setUserGroupId(user.getGroupId());
            authToken.setToken(SecurityUtil.generateToken());
            Date currentTime = new Date();
            authToken.setCreateTime(currentTime);
            authToken.setExpireTime(new Date(currentTime.getTime() + TOKEN_DURATION));
            authTokenMapper.insert(authToken);

            // 保护隐私, 将密码设为空
            user.setPassword(null);

            // 准备返回数据
            UserAuthExecution userAuthExecution = new UserAuthExecution(UserAuthStateEnum.SUCCESS);
            userAuthExecution.setUser(user);
            userAuthExecution.setToken(authToken.getToken());

            return userAuthExecution;
        }

    }

    @Override
    @Transactional
    public UserRegistrationExecution register(String email, String profileName, String password) {

        // 数据校验
        if (!ValidationUtil.isValidEmail(email) ||
                !ValidationUtil.isValidProfileName(profileName) ||
                !ValidationUtil.isValidPassword(password)) {
            return new UserRegistrationExecution(UserRegistrationStateEnum.INVALID_REGISTRATION_PARAMETERS);
        }

        // 邮箱已注册
        if (userMapper.selectByEmail(email) != null) {
            return new UserRegistrationExecution(UserRegistrationStateEnum.USER_ALREADY_EXISTS);
        }

        // 开始注册
        User user = new User();
        user.setEmail(email);
        user.setProfileName(profileName);
        user.setPassword(SecurityUtil.hashpw(password));
        user.setProfileImgSrc("/images/avatars/avatar_default.png");
        user.setGroupId(0);
        user.setStatus(0);
        user.setReputation(0);
        user.setRegisterTime(new Date());
        userMapper.insert(user);
        user = userMapper.selectById(user.getId());
        return new UserRegistrationExecution(UserRegistrationStateEnum.SUCCESS, user);
    }

    @Override
    public User getUserIdByToken(String token) {
        AuthToken authToken = authTokenMapper.selectByToken(token);
        if (authToken == null) {
            return null;
        }
        return userMapper.selectById(authToken.getUserId());
    }

    @Override
    @Transactional
    public void updateReputation(Integer userId) {

        User user = userMapper.selectById(userId);
        List<Question> questions = questionMapper.selectByUserId(userId);
        List<Answer> answers = answerMapper.selectByUserId(userId);
        int reputation = 0;
        for (Question question : questions) {
            reputation += Math.sqrt(question.getApprovals() - question.getDisapprovals()) * 3.0;
            reputation += question.getQuestionComments().size() * 1.0;
            for (Answer answer : question.getAnswers()) {
                reputation += Math.sqrt(answer.getApprovals() - answer.getDisapprovals()) * 0.5;
            }
        }
        for (Answer answer : answers) {
            reputation += Math.sqrt(answer.getApprovals() - answer.getDisapprovals()) * 3.0;
            reputation += answer.getAnswerComments().size() * 1.0;
        }
        reputation += questionCommentMapper.countCommentsByUserId(userId) * 10.0;
        reputation += answerCommentMapper.countCommentsByUserId(userId) * 10.0;
        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setReputation(reputation);
        userMapper.updateById(updateUser);
    }

    @Override
    public void updateProfileImg(User user, MultipartFile profileImg) throws IOException {
        String imageBasePath = "C:\\Users\\Alex\\Documents\\workspace\\qa-system\\src\\main\\resources\\static\\images\\avatars\\";
        String contentType = profileImg.getContentType().split("/")[1];
        File file = new File(imageBasePath + user.getId()+"."+contentType);
        profileImg.transferTo(file);
        user.setId(user.getId());
        user.setProfileImgSrc("/images/avatars/"+file.getName());
        userMapper.updateById(user);
    }

    @Override
    public Map<String, Object> changePassword(User user, String oldPassword, String newPassword) {
        Map<String, Object> map = new HashMap<>(2);
        if (!ValidationUtil.isValidPassword(newPassword)) {
            map.put("success", false);
            map.put("message", "新密码不合法");
        } else if (!SecurityUtil.checkpw(oldPassword, user.getPassword())) {
            map.put("success", false);
            map.put("message", "密码不正确");
        } else {
            user.setPassword(SecurityUtil.hashpw(newPassword));
            userMapper.updateById(user);
            map.put("success", true);
        }
        return map;
    }

}
