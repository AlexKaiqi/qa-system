package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Answer;
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

    @Test
    @Transactional
    public void insert() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setContent("test comment");
        answerComment.setCreateTime(new Date());
        assertThat(answerCommentMapper.insert(answerComment), is(1));
        System.out.println(answerCommentMapper.selectById(answerComment.getId()));

    }

    @Test
    @Transactional
    public void deleteById() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setContent("test comment");
        answerComment.setCreateTime(new Date());
        assertThat(answerCommentMapper.insert(answerComment), is(1));
        assertThat(answerCommentMapper.deleteById(answerComment.getId()), is(1));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        assertThat(answerCommentMapper.deleteByAnswerId(1), is(2));
    }

    @Test
    @Transactional
    public void updateById() {
        AnswerComment answerComment = new AnswerComment();
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setContent("test comment");
        answerComment.setCreateTime(new Date());
        assertThat(answerCommentMapper.insert(answerComment), is(1));
        answerComment.setUserId(1);
        answerComment.setAnswerId(1);
        answerComment.setContent("new test comment");
        assertThat(answerCommentMapper.updateById(answerComment), is(1));
        answerComment = answerCommentMapper.selectById(answerComment.getId());
        assertThat(answerComment.getContent(), is("new test comment"));
        System.out.println(answerComment);
    }

    @Test
    @Transactional
    public void selectById() {
        AnswerComment answerComment = answerCommentMapper.selectById(1);
        assertNotNull(answerComment);
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