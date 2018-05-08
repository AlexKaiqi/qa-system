package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.*;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.MessageService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private QuestionSubscriptionMapper questionSubscriptionMapper;

    private UserSubscriptionMapper userSubscriptionMapper;

    private QuestionMapper questionMapper;

    private MessageMapper messageMapper;

    private UserMapper userMapper;

    @Autowired
    public void setQuestionSubscriptionMapper(QuestionSubscriptionMapper questionSubscriptionMapper) {
        this.questionSubscriptionMapper = questionSubscriptionMapper;
    }

    @Autowired
    public void setUserSubscriptionMapper(UserSubscriptionMapper userSubscriptionMapper) {
        this.userSubscriptionMapper = userSubscriptionMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Autowired
    public void setMessageMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public List<Message> addQuestionHasNewAnswerMessage(Answer answer) {
        Integer questionId = answer.getQuestionId();
        Question question = questionMapper.selectById(questionId);
        List<QuestionSubscription> subscriptions = questionSubscriptionMapper.selectByQuestionId(questionId);
        List<Message> messages = new ArrayList<>();
        for (QuestionSubscription s : subscriptions) {
            Message message = new Message();
            message.setType(0);
            message.setReceiverId(s.getUserId());
            message.setContent("<a href='/questions/" + question.getId() + "/" + question.getTitle() + "'>你关注的问题: " + question.getTitle() + " 有了新回答</a>");
            message.setStatus(0);
            message.setSendTime(new Date());
            messages.add(message);
            messageMapper.insert(message);
        }
        return messages;
    }

    @Override
    public List<Message> addUserPostNewAnswerMessage(Answer answer) {
        Integer questionId = answer.getQuestionId();
        User user = userMapper.selectById(answer.getUserId());
        Question question = questionMapper.selectById(questionId);
        List<UserSubscription> subscriptions = userSubscriptionMapper.selectByWatchedUserId(answer.getUserId());
        List<Message> messages = new ArrayList<>();
        for (UserSubscription s : subscriptions) {
            Message message = new Message();
            message.setType(0);
            message.setReceiverId(s.getUserId());
            message.setContent("<a href='/questions/" + question.getId() + "/" + question.getTitle() + "'>你关注的用户: " + user.getProfileName() + " 有了新回答</a>");
            message.setStatus(0);
            message.setSendTime(new Date());
            messages.add(message);
            messageMapper.insert(message);
        }
        return messages;
    }

    @Override
    public List<Message> getMessagesByReceiverId(Integer receiverId) {
        List<Message> messages = messageMapper.selectByReceiverId(receiverId);
        return messages;
    }

    @Override
    public Message deleteMessageById(User user, Integer messageId) throws NotFoundException, AuthException {
        Message message = messageMapper.selectById(messageId);
        if (message == null) {
            throw new NotFoundException("找不到该消息. message: " + messageId);
        }
        if (!message.getReceiverId().equals(user.getId())) {
            throw new AuthException("没有删除消息的权限. userId: " + user.getId());
        }
        return message;
    }

    @Override
    public List<Message> addUserPostNewQuestionMessage(Question question) {
        User user = userMapper.selectById(question.getUserId());
        List<UserSubscription> subscriptions = userSubscriptionMapper.selectByWatchedUserId(question.getUserId());
        List<Message> messages = new ArrayList<>();
        for (UserSubscription s : subscriptions) {
            Message message = new Message();
            message.setType(0);
            message.setReceiverId(s.getUserId());
            message.setContent("<a href='/questions/" + question.getId() + "/" + question.getTitle() + "'>你关注的用户: " + user.getProfileName() + " 提出了新问题</a>");
            message.setStatus(0);
            message.setSendTime(new Date());
            messages.add(message);
            messageMapper.insert(message);
        }
        return messages;
    }


}
