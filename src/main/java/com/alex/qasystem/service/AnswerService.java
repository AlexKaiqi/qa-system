package com.alex.qasystem.service;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerApproval;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.User;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;

public interface AnswerService {

    Answer addAnswer(User user, Integer questionId, String content);

    Answer updateAnswerContent(User user, Integer answerId, String content) throws NotFoundException, AuthException;

    Answer deleteAnswer(User user, Integer answerId) throws NotFoundException, AuthException;

    AnswerComment addAnswerComment(User user, Integer answerId, String content);

    AnswerComment deleteAnswerComment(User user, Integer answerCommentId) throws NotFoundException, AuthException;

    AnswerApproval addAnswerApproval(User user, Integer answerId, Integer type);

}
