package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionComment;
import com.alex.qasystem.entity.User;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionCommentMapperTest {
    @Autowired
    private QuestionCommentMapper questionCommentMapper;
    @Autowired
    private UserMapper userMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setUserId(1);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date(DATETIME));
        questionComment.setContent("a new comment");
        questionComment.setUser(userMapper.selectSimpleById(1));
        assertThat(questionCommentMapper.insert(questionComment), is(1));
        assertThat(questionCommentMapper.selectById(questionComment.getId()).toString(), is(questionComment.toString()));
        System.out.println(questionCommentMapper.selectById(questionComment.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        assertThat(questionCommentMapper.deleteById(1), is(1));
        assertNull(questionCommentMapper.selectById(1));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        assertThat(questionCommentMapper.deleteByQuestionId(1), is(2));
        assertThat(questionCommentMapper.selectByQuestionId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(questionCommentMapper.selectById(1));
        QuestionComment questionComment = new QuestionComment();
        questionComment.setId(1);
        questionComment.setUserId(1);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date(DATETIME));
        questionComment.setContent("updated comment");
        questionComment.setLastEditTime(new Date(DATETIME));
        questionComment.setUser(userMapper.selectSimpleById(1));
        assertThat(questionCommentMapper.updateById(questionComment), is(1));
        assertThat(questionCommentMapper.selectById(1).toString(), is(questionComment.toString()));
        System.out.println(questionCommentMapper.selectById(1));
    }

    @Test
    @Transactional
    public void selectById() {
        QuestionComment questionComment = new QuestionComment();
        questionComment.setId(1);
        questionComment.setUserId(3);
        questionComment.setQuestionId(1);
        questionComment.setCreateTime(new Date(DATETIME));
        questionComment.setContent("test question comment 1");
        questionComment.setUser(userMapper.selectSimpleById(3));
        assertThat(questionCommentMapper.selectById(1).toString(), is(questionComment.toString()));
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