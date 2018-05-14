package com.alex.qasystem.service;

import com.alex.qasystem.entity.*;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface MessageService {

    List<Message> getMessagesByReceiverId(Integer receiverId);

    Message deleteMessageById(User user, Integer messageId) throws NotFoundException, AuthException;

    List<Message> addUserPostNewQuestionMessage(Question question);

    List<Message> addQuestionHasNewAnswerMessage(Answer answer);

    List<Message> addUserPostNewAnswerMessage(Answer answer);

    Message addNewMedalMessage(MedalRecord medalRecord);

}
