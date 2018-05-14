package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.AnswerApprovalMapper;
import com.alex.qasystem.dao.AnswerCommentMapper;
import com.alex.qasystem.dao.AnswerMapper;
import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerApproval;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.AnswerService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;
import java.util.Date;

@Service
public class AnswerServiceImpl implements AnswerService {

    private AnswerMapper answerMapper;
    private AnswerCommentMapper answerCommentMapper;
    private AnswerApprovalMapper answerApprovalMapper;

    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {
        this.answerMapper = answerMapper;
    }

    @Autowired
    public void setAnswerCommentMapper(AnswerCommentMapper answerCommentMapper) {
        this.answerCommentMapper = answerCommentMapper;
    }

    @Autowired
    public void setAnswerApprovalMapper(AnswerApprovalMapper answerApprovalMapper) {
        this.answerApprovalMapper = answerApprovalMapper;
    }


    @Override
    @Transactional
    public Answer addAnswer(User user, Integer questionId, String content) {
        Integer userId = user.getId();
        Answer answer = new Answer();
        answer.setUserId(userId);
        answer.setQuestionId(questionId);
        answer.setContent(content);
        answer.setStatus(0);
        answer.setCreateTime(new Date());
        answerMapper.insert(answer);
        return answer;
    }

    @Override
    @Transactional
    public AnswerComment addAnswerComment(User user, Integer answerId, String content) {
        Integer userId = user.getId();
        AnswerComment answerComment = new AnswerComment();
        answerComment.setUserId(userId);
        answerComment.setAnswerId(answerId);
        answerComment.setContent(content);
        answerComment.setCreateTime(new Date());
        answerCommentMapper.insert(answerComment);
        return answerComment;
    }

    @Override
    @Transactional
    public AnswerApproval addAnswerApproval(User user, Integer answerId, Integer type) {
        Integer userId = user.getId();
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(userId);
        answerApproval.setAnswerId(answerId);
        answerApproval.setCreateTime(new Date());
        answerApproval.setType(type);
        answerApprovalMapper.insert(answerApproval);
        return answerApproval;
    }

    @Override
    public Answer updateAnswerContent(User user, Integer answerId, String content) throws NotFoundException, AuthException {
        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        Answer answer = answerMapper.selectById(answerId);
        if (answer == null) {
            throw new NotFoundException("回答不存在, answerId: " + answerId);
        }
        if (!answer.getUserId().equals(userId) && user.getGroupId() != 1) {
            throw new AuthException("没有修改回答的权限, userId: " + userId);
        }
        answer.setId(answerId);
        answer.setContent(content);
        answerMapper.updateById(answer);
        return answer;
    }

    @Override
    public Answer deleteAnswer(User user, Integer answerId) throws NotFoundException, AuthException {
        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        Answer answer = answerMapper.selectById(answerId);
        if (answer == null) {
            throw new NotFoundException("回答不存在, answerId: " + answerId);
        }
        if (!answer.getUserId().equals(userId) && user.getGroupId() != 1) {
            throw new AuthException("没有修改回答的权限, userId: " + userId);
        }
        answerCommentMapper.deleteByAnswerId(answerId);
        answerApprovalMapper.deleteByAnswerId(answerId);
        answerMapper.deleteById(answerId);
        return answer;
    }

    @Override
    public AnswerComment deleteAnswerComment(User user, Integer answerCommentId) throws NotFoundException, AuthException {
        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        AnswerComment answerComment = answerCommentMapper.selectById(answerCommentId);
        if (answerComment == null) {
            throw new NotFoundException("评论不存在, answerCommentId: " + answerCommentId);
        }
        if (!answerComment.getUserId().equals(userId) && user.getGroupId() != 1) {
            throw new AuthException("没有删除评论的权限, userId: " + userId);
        }
        answerCommentMapper.deleteById(answerCommentId);
        return answerComment;
    }

}
