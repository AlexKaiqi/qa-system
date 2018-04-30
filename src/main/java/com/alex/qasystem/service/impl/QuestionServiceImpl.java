package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.*;
import com.alex.qasystem.entity.*;
import com.alex.qasystem.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
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
    public void setTagMapper(TagMapper tagMapper) {this.tagMapper = tagMapper;}
    @Autowired
    public void setQuestionCommentMapper(QuestionCommentMapper questionCommentMapper) {this.questionCommentMapper = questionCommentMapper;}
    @Autowired
    public void setQuestionApprovalMapper(QuestionApprovalMapper questionApprovalMapper) {this.questionApprovalMapper = questionApprovalMapper;}
    @Autowired
    public void setQuestionMapper(QuestionMapper questionMapper) {this.questionMapper = questionMapper;}
    @Autowired
    public void setAnswerMapper(AnswerMapper answerMapper) {this.answerMapper = answerMapper;}
    @Autowired
    public void setAnswerCommentMapper(AnswerCommentMapper answerCommentMapper) {
        this.answerCommentMapper = answerCommentMapper;
    }
    @Autowired
    public void setAnswerApprovalMapper(AnswerApprovalMapper answerApprovalMapper) {
        this.answerApprovalMapper = answerApprovalMapper;
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
   public Question addQuestion(Integer userId, String title, String description, List<String> tags) {
       // 插入question
       List<Tag> tagList = new ArrayList<>();
        for (String tagTitle: tags) {
            Tag tag =new Tag(tagTitle);
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
    public Answer addAnswer(Integer userId, Integer questionId, String content) {
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
    public QuestionComment addQuestionComment(Integer userId, Integer questionId, String content) {
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
    public AnswerComment addAnswerComment(Integer userId, Integer answerId, String content) {
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
    public QuestionApproval addQuestionApproval(Integer userId, Integer questionId, Integer type) {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(userId);
        questionApproval.setQuestionId(questionId);
        questionApproval.setCreateTime(new Date());
        questionApproval.setType(type);
        questionApprovalMapper.insert(questionApproval);
        return questionApproval;
    }

    @Override
    @Transactional
    public AnswerApproval addAnswerApproval(Integer userId, Integer answerId, Integer type) {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(userId);
        answerApproval.setAnswerId(answerId);
        answerApproval.setCreateTime(new Date());
        answerApproval.setType(type);
        answerApprovalMapper.insert(answerApproval);
        return answerApproval;
    }

}
