package com.alex.qasystem.service;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionSubscription;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface QuestionSubscriptionService {

    List<Question> getSubscribedQuestionsByUserId(Integer userId);

    QuestionSubscription addQuestionSubscription(Integer userId, Integer questionId);

    QuestionSubscription deleteQuestionSubscriptionById(User user, Integer id) throws NotFoundException, AuthException;


}
