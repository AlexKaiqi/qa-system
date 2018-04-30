package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionComment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionCommentMapperTest {
    @Autowired
    private QuestionCommentMapper questionCommentMapper;


    @Test
    @Transactional
    public void insert() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setUserId(1);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date());
        questionComment.setContent("a new comment");
        assertThat(questionCommentMapper.insert(questionComment), is(1));
        System.out.println(questionCommentMapper.selectByQuestionId(1));
    }

    @Test
    @Transactional
    public void deleteById() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setUserId(1);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date());
        questionComment.setContent("a new comment");
        assertThat(questionCommentMapper.insert(questionComment), is(1));
        assertThat(questionCommentMapper.deleteById(questionComment.getId()), is(1));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        assertThat(questionCommentMapper.deleteByQuestionId(1), is(2));
    }

    @Test
    @Transactional
    public void updateById() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setUserId(1);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date());
        questionComment.setContent("a new comment");
        assertThat(questionCommentMapper.insert(questionComment), is(1));
        questionComment.setContent("updated comment");
        questionComment.setLastEditTime(new Date());
        assertThat(questionCommentMapper.updateById(questionComment), is(1));
        System.out.println(questionCommentMapper.selectById(questionComment.getId()));
    }

    @Test
    @Transactional
    public void selectById() {
        QuestionComment questionComment = questionCommentMapper.selectById(1);
        assertNotNull(questionComment);
        System.out.println(questionComment);
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        List<QuestionComment> questionComments = questionCommentMapper.selectByQuestionId(1);
        assertThat(questionComments.size(), is(2));
        System.out.println(questionComments);
    }
}