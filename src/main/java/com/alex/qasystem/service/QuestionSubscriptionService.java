package com.alex.qasystem.service;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionSubscription;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface QuestionSubscriptionService {

    List<Question> getSubscribedQuestionsByUserId(Integer userId);

    QuestionSubscription addQuestionSubscription(User user, Integer questionId);

    QuestionSubscription deleteByUserIdAndQuestionId(User user, Integer questionId) throws NotFoundException, AuthException;


}
