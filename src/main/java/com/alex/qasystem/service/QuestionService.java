package com.alex.qasystem.service;

import com.alex.qasystem.dao.QuestionMapper;
import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface QuestionService {


    List<Question> queryQuestionByTitleRegexp(String titleRegexp);

    List<Question> queryQuestionByTagRegexp(String tagRegexp);

    Question getQuestionById(Integer id);

    Question addQuestion(Integer userId, String title, String description, List<String> tags);

    Answer addAnswer(Integer userId, Integer questionId, String content);

    QuestionComment addQuestionComment(Integer userId, Integer questionId, String content);

    AnswerComment addAnswerComment(Integer userId, Integer answerId, String content);

    QuestionApproval addQuestionApproval(Integer userId, Integer questionId, Integer type);

    AnswerApproval addAnswerApproval(Integer userId, Integer answerId, Integer type);

    Question updateQuestionContent(Integer questionId, String title, String description);

    Answer updateAnswerContent(Integer answerId, String content);
}
