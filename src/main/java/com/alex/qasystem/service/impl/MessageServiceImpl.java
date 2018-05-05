package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dao.QuestionSubscriptionMapper;
import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Message;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionSubscription;
import com.alex.qasystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MessageServiceImpl implements MessageService {

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
    public List<Message> addNewAnswerMessage(Answer answer) {
        Integer questionId = answer.getQuestionId();
        Question question = questionMapper.selectById(questionId);
        List<QuestionSubscription> subscriptions = questionSubscriptionMapper.selectByQuestionId(questionId);
        for (QuestionSubscription subscription: subscriptions) {
            Message message = new Message();
            message.setType(0);
            message.setReceiverId(subscription.getUserId());
        }
        return null;
    }

    @Override
    public List<Message> addNewQuestionMessage(Question question) {
        return null;
    }
}
