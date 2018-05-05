package com.alex.qasystem.service;

import com.alex.qasystem.entity.*;

import java.util.List;

public interface QuestionSubscriptionService {

    List<Question> getSubscribedQuestionsByUserId(Integer userId);

    QuestionSubscription addQuestionSubscription(Integer userId, Integer questionId);

    QuestionSubscription deleteQuestionSubscriptionById(User user, Integer id);


}
