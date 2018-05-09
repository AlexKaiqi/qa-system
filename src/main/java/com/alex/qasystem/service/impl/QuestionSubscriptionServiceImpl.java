package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dao.QuestionSubscriptionMapper;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionSubscription;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.QuestionSubscriptionService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
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
        for (QuestionSubscription subscription : subscriptions) {
            questions.add(questionMapper.selectById(subscription.getQuestionId()));
        }
        return questions;
    }


    @Override
    public QuestionSubscription addQuestionSubscription(User user, Integer questionId) {
        // 检查是否已经收藏, 如果有直接返回.
        List<QuestionSubscription> subscriptions = questionSubscriptionMapper.selectByUserId(user.getId());
        for (QuestionSubscription subscription : subscriptions) {
            if (subscription.getQuestionId().equals(questionId)) {
                return subscription;
            }
        }
        QuestionSubscription subscription = new QuestionSubscription();
        subscription.setUserId(user.getId());
        subscription.setQuestionId(questionId);
        questionSubscriptionMapper.insert(subscription);
        return subscription;
    }

    @Override
    public QuestionSubscription deleteByUserIdAndQuestionId(User user, Integer questionId) throws NotFoundException, AuthException {
        QuestionSubscription subscription = questionSubscriptionMapper.selectByUserIdAndQuestionId(user.getId(), questionId);
        if (subscription == null) {
            throw new NotFoundException("找不到该问题关注. questionId: " + questionId);
        }
        if (!subscription.getUserId().equals(user.getId())) {
            throw new AuthException("没有删除权限. userId: " + user.getId());
        }
        questionSubscriptionMapper.deleteById(subscription.getId());
        return subscription;
    }

}
