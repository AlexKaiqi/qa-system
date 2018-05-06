package com.alex.qasystem.service;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionApproval;
import com.alex.qasystem.entity.QuestionComment;
import com.alex.qasystem.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionService {


    List<Question> queryQuestionByTitleRegexp(String titleRegexp);

    List<Question> queryQuestionByTagRegexp(String tagRegexp);

    Question getQuestionById(Integer id);

    Question addQuestion(User user, String title, String description, List<String> tags);

    Question updateQuestionContent(User user, Integer questionId, String title, String description, List<String> tags);

    Question closeQuestion(User user, Integer questionId);

    QuestionComment addQuestionComment(User user, Integer questionId, String content);

    QuestionComment deleteQuestionComment(User user, Integer questionCommentId);

    QuestionApproval addQuestionApproval(User user, Integer questionId, Integer type);

}
