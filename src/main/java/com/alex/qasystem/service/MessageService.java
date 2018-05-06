package com.alex.qasystem.service;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Message;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;

import java.util.List;

public interface MessageService {

    List<Message> getMessagesByReceiverId(Integer receiverId);

    Message deleteMessageById(User user, Integer messageId);

    List<Message> addUserPostNewQuestionMessage(Question question);

    List<Message> addQuestionHasNewAnswerMessage(Answer answer);

    List<Message> addUserPostNewAnswerMessage(Answer answer);

}
