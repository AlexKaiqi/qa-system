package com.alex.qasystem.service;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Message;
import com.alex.qasystem.entity.Question;

import java.util.List;

public interface MessageService {

    List<Message> addNewQuestionMessage(Question question);

    List<Message> addNewAnswerMessage(Answer answer);




}
