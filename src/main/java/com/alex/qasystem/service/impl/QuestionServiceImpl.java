package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.*;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.QuestionService;
import org.apache.ibatis.javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private TagMapper tagMapper;
    private QuestionCommentMapper questionCommentMapper;
    private QuestionApprovalMapper questionApprovalMapper;
    private QuestionMapper questionMapper;
    private AnswerMapper answerMapper;
    private AnswerCommentMapper answerCommentMapper;
    private AnswerApprovalMapper answerApprovalMapper;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Autowired
    public void setQuestionCommentMapper(QuestionCommentMapper questionCommentMapper) {
        this.questionCommentMapper = questionCommentMapper;
    }

    @Autowired
    public void setQuestionApprovalMapper(QuestionApprovalMapper questionApprovalMapper) {
        this.questionApprovalMapper = questionApprovalMapper;
    }

    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {
        this.questionMapper = questionMapper;
    }


    @Override
    public List<Question> queryQuestionByTitleRegexp(String titleRegexp) {
        return questionMapper.selectByTitleRegexp(titleRegexp);
    }

    @Override
    public List<Question> queryQuestionByTagRegexp(String tagRegexp) {
        return questionMapper.selectByTagRegexp(tagRegexp);
    }

    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectById(id);
    }

    @Override
    @Transactional
    public Question addQuestion(User user, String title, String description, List<String> tags) {

        Integer userId = user.getId();
        // 插入question
        List<Tag> tagList = new ArrayList<>();
        for (String tagTitle : tags) {
            Tag tag = new Tag(tagTitle);
            try {
                tagMapper.insert(tag);
            } catch (DuplicateKeyException e) {
                log.debug("Tag already exists: " + tagTitle);
                tag = tagMapper.selectByTitle(tagTitle);
            }
            tagList.add(tag);
        }
        // 插入question
        Question question = new Question();
        question.setUserId(userId);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreateTime(new Date());
        question.setStatus(0);
        questionMapper.insert(question);

        // 插入question和tag的关联关系
        List<Integer> tagIds = tagList.stream().map(Tag::getId).collect(Collectors.toList());
        questionMapper.insertTagReferences(question.getId(), tagIds);

        return questionMapper.selectById(question.getId());
    }


    @Override
    @Transactional
    public QuestionComment addQuestionComment(User user, Integer questionId, String content) {
        Integer userId = user.getId();
        QuestionComment questionComment = new QuestionComment();
        questionComment.setUserId(userId);
        questionComment.setQuestionId(questionId);
        questionComment.setContent(content);
        questionComment.setCreateTime(new Date());
        questionCommentMapper.insert(questionComment);
        return questionComment;
    }


    @Override
    @Transactional
    public QuestionApproval addQuestionApproval(User user, Integer questionId, Integer type) {
        Integer userId = user.getId();
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(userId);
        questionApproval.setQuestionId(questionId);
        questionApproval.setCreateTime(new Date());
        questionApproval.setType(type);
        questionApprovalMapper.insert(questionApproval);
        return questionApproval;
    }


    @Override
    public Question updateQuestionContent(User user, Integer questionId, String title, String description, List<String> tags) throws NotFoundException, AuthException {

        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new NotFoundException("问题不存在, questionId: " + questionId);
        }
        if (!question.getUserId().equals(userId) && 1 != groupId) {
            throw new AuthException("没有修改问题的权限, userId: " + userId);
        }
        question.setId(questionId);
        question.setTitle(title);
        question.setDescription(description);
        questionMapper.updateById(question);
        if (tags != null) {
            // 删除现有的标签
            questionMapper.deleteTagReferencesByQuestionId(questionId);

            List<Tag> tagList = new ArrayList<>();
            // 插入标签
            for (String tagTitle : tags) {
                Tag tag = new Tag(tagTitle);
                try {
                    tagMapper.insert(tag);
                } catch (DuplicateKeyException e) {
                    log.debug("Tag already exists: " + tagTitle);
                    tag = tagMapper.selectByTitle(tagTitle);
                }
                tagList.add(tag);
            }
            // 插入question和tag的关联关系
            List<Integer> tagIds = tagList.stream().map(Tag::getId).collect(Collectors.toList());
            questionMapper.insertTagReferences(question.getId(), tagIds);
            question.setTags(tagList);
        }

        return question;
    }


    @Override
    public Question closeQuestion(User user, Integer questionId) throws NotFoundException, AuthException {
        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        Question question = questionMapper.selectById(questionId);
        if (question == null) {
            throw new NotFoundException("问题不存在, questionId: " + questionId);
        }
        if (!question.getUserId().equals(userId) && 1 != groupId) {
            throw new AuthException("没有修改问题的权限, userId: " + userId);
        }
        question.setStatus(1);
        questionMapper.updateById(question);
        return question;
    }

    @Override
    public QuestionComment deleteQuestionComment(User user, Integer questionCommentId) throws NotFoundException, AuthException {
        Integer userId = user.getId();
        Integer groupId = user.getGroupId();
        QuestionComment questionComment = questionCommentMapper.selectById(questionCommentId);
        if (questionComment == null) {
            throw new NotFoundException("评论不存在, questionCommentId: " + questionCommentId);
        }
        if (!questionComment.getUserId().equals(userId) && 1 != groupId) {
            throw new AuthException("没有删除评论的权限, userId: " + userId);
        }
        questionCommentMapper.deleteById(questionCommentId);
        return questionComment;
    }


}
