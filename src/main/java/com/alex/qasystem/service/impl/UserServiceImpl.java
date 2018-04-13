package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.AuthTokenMapper;
import com.alex.qasystem.dao.UserMapper;
import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegisterExecution;
import com.alex.qasystem.entity.AuthToken;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserAuthStateEnum;
import com.alex.qasystem.enums.UserRegistrationStateEnum;
import com.alex.qasystem.service.UserService;
import com.alex.qasystem.util.SecurityUtil;
import com.alex.qasystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Alex
 */
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private AuthTokenMapper authTokenMapper;
    /**
     * token 有效时间 两个小时
     */
    private final static long TOKEN_DURATION = 7200000;

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

        // 缺少验证信息
        if (email == null || password == null) {
            return new UserAuthExecution(UserAuthStateEnum.NULL_AUTH_INFO);
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

        // 验证成功
        else {

            // 为验证通过的用户设置token
            AuthToken authToken = new AuthToken();
            authToken.setUserId(user.getId());
            authToken.setUserGroupId(user.getGroupId());
            authToken.setToken(SecurityUtil.generateToken());
            Date currentTime = new Date();
            authToken.setCreateTime(currentTime);
            authToken.setExpireTime(new Date(currentTime.getTime() + TOKEN_DURATION));
            Integer authTokenId = authTokenMapper.insert(authToken);
            authToken.setId(authTokenId);

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
    public UserRegisterExecution register(String email, String profileName, String password) {

        // 数据校验
        if (!ValidationUtil.isValidEmail(email) ||
                !ValidationUtil.isValidProfileName(profileName) ||
                !ValidationUtil.isValidPassword(password)) {
            return new UserRegisterExecution(UserRegistrationStateEnum.INVALID_REGISTRATION_INFO);
        }

        // 邮箱已注册
        if (userMapper.selectByEmail(email) != null) {
            return new UserRegisterExecution(UserRegistrationStateEnum.USER_ALREADY_EXISTS);
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
        return new UserRegisterExecution(UserRegistrationStateEnum.SUCCESS, user);
    }

}
