package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.AnswerComment;
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
public class AnswerCommentMapperTest {
    @Autowired
    private AnswerCommentMapper answerCommentMapper;
    @Autowired
    private UserMapper userMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setCreateTime(new Date(DATETIME));
        answerComment.setContent("a new comment");
        answerComment.setUser(userMapper.selectSimpleById(1));
        assertThat(answerCommentMapper.insert(answerComment), is(1));
        assertThat(answerCommentMapper.selectById(answerComment.getId()).toString(), is(answerComment.toString()));
        System.out.println(answerCommentMapper.selectById(answerComment.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        assertThat(answerCommentMapper.deleteById(1), is(1));
        assertNull(answerCommentMapper.selectById(1));
    }

    @Test
    @Transactional
    public void deleteByAnswerId() {
        assertThat(answerCommentMapper.deleteByAnswerId(1), is(2));
        assertThat(answerCommentMapper.selectByAnswerId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(answerCommentMapper.selectById(1));
        AnswerComment answerComment = new AnswerComment();
        answerComment.setId(1);
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setCreateTime(new Date(DATETIME));
        answerComment.setContent("updated comment");
        answerComment.setLastEditTime(new Date(DATETIME));
        answerComment.setUser(userMapper.selectSimpleById(1));
        assertThat(answerCommentMapper.updateById(answerComment), is(1));
        assertThat(answerCommentMapper.selectById(1).toString(), is(answerComment.toString()));
        System.out.println(answerCommentMapper.selectById(1));
    }

    @Test
    @Transactional
    public void selectById() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setId(1);
        answerComment.setUserId(2);
        answerComment.setAnswerId(1);
        answerComment.setCreateTime(new Date(DATETIME));
        answerComment.setContent("test answer comment 1");
        answerComment.setUser(userMapper.selectSimpleById(2));
        assertThat(answerCommentMapper.selectById(1).toString(), is(answerComment.toString()));
        System.out.println(answerComment);
    }

    @Test
    @Transactional
    public void selectByAnswerId() {
        List<AnswerComment> answerComments = answerCommentMapper.selectByAnswerId(1);
        assertThat(answerComments.size(), is(2));
        System.out.println(answerComments);
    }
}