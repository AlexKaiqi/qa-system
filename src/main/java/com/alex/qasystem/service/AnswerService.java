package com.alex.qasystem.service;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerApproval;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.User;

public interface AnswerService {

    Answer addAnswer(User user, Integer questionId, String content);

    Answer updateAnswerContent(User user, Integer answerId, String content);

    Answer deleteAnswer(User user, Integer answerId);

    AnswerComment addAnswerComment(User user, Integer answerId, String content);

    AnswerComment deleteAnswerComment(User user, Integer answerId, Integer answerCommentId);

    AnswerApproval addAnswerApproval(User user, Integer answerId, Integer type);

}
