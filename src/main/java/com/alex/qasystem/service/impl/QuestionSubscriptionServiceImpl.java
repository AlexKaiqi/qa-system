package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dao.QuestionSubscriptionMapper;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.QuestionSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionSubscriptionServiceImpl implements QuestionSubscriptionService {
    private QuestionSubscriptionMapper questionSubscriptionMapper;

    private QuestionMapper questionMapper;

    @Autowired
    public void setQuestionSubscriptionMapper(QuestionSubscriptionMapper questionSubscriptionMapper) {
        this.questionSubscriptionMapper = questionSubscriptionMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }

    @Override
    public List<Question> getSubscribedQuestionsByUserId(Integer userId) {
        List<QuestionSubscription> subscriptions = questionSubscriptionMapper.selectByUserId(userId);
        List<Question> questions = new ArrayList<>();
        for (QuestionSubscription subscription: subscriptions) {
            questions.add(questionMapper.selectById(subscription.getQuestionId()));
        }
        return questions;
    }



    @Override
    public QuestionSubscription addQuestionSubscription(Integer userId, Integer questionId) {
        QuestionSubscription subscription = new QuestionSubscription();
        subscription.setUserId(userId);
        subscription.setQuestionId(questionId);
        questionSubscriptionMapper.insert(subscription);
        return subscription;
    }

    @Override
    public QuestionSubscription deleteQuestionSubscriptionById(User user, Integer questionSubscriptionId) {
        QuestionSubscription subscription = questionSubscriptionMapper.selectById(questionSubscriptionId);
        if(subscription == null) {
            throw new RuntimeException("找不到该问题关注. questionSubscriptionId: " + questionSubscriptionId);
        }
        if(!subscription.getUserId().equals(user.getId())) {
            throw new RuntimeException("没有删除权限. userId: " + user.getId());
        }
        questionSubscriptionMapper.deleteById(questionSubscriptionId);
        return subscription;
    }

}
