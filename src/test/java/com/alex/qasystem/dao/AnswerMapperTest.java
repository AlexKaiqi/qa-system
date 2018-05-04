package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerMapperTest {
    @Autowired
    private AnswerMapper answerMapper;
    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(1);
        answer.setContent("test content");
        answer.setStatus(0);
        answer.setCreateTime(new Date(DATETIME));
        assertThat(answerMapper.insert(answer), is(1));
        System.out.println(answer);
    }

    @Test
    @Transactional
    public void deleteById() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(1);
        answer.setCreateTime(new Date(DATETIME));
        answer.setContent("test content");
        answer.setStatus(0);
        assertThat(answerMapper.insert(answer), is(1));
        assertThat(answerMapper.deleteById(answer.getId()), is(1));
        assertNull(answerMapper.selectById(answer.getId()));
        System.out.println(answer);
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(2);
        answer.setCreateTime(new Date(DATETIME));
        answer.setContent("test content");
        answer.setStatus(0);
        answerMapper.insert(answer);
        assertThat(answerMapper.deleteByQuestionId(2), is(1));
        assertThat(answerMapper.selectByQuestionId(2).size(), is(0));
    }

    @Test
    @Transactional
    public void updateById() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(answerMapper.selectById(1));
        Answer updated = new Answer();
        updated.setId(1);
        updated.setUserId(2);
        updated.setQuestionId(2);
        updated.setCreateTime(new Date(DATETIME));
        updated.setLastEditTime(new Date(DATETIME));
        updated.setContent("test content");
        updated.setStatus(1);
        assertThat(answerMapper.updateById(updated), is(1));
        Answer answer = answerMapper.selectById(1);
        assertThat(answer.getId(), is(updated.getId()));
        assertThat(answer.getUserId(), is(updated.getUserId()));
        assertThat(answer.getQuestionId(), is(updated.getQuestionId()));
        assertThat(answer.getContent(), is(updated.getContent()));
        assertThat(answer.getStatus(), is(updated.getStatus()));
        assertThat(f.format(answer.getCreateTime()), is(f.format(updated.getCreateTime())));
        assertThat(f.format(answer.getLastEditTime()), is(f.format(updated.getLastEditTime())));
        System.out.println(updated);
    }

    @Test
    @Transactional
    public void selectById() {
        Answer answer = answerMapper.selectById(1);
        assertThat(answer.getId(), is(1));
        assertThat(answer.getUserId(), is(2));
        assertThat(answer.getQuestionId(), is(1));
        assertThat(answer.getContent(), is("answer content 1"));
        assertThat(answer.getStatus(), is(0));
        assertThat(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(answer.getCreateTime()), is("2017-09-14 00:49"));
        System.out.println(answer);
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        List<Answer> answers = answerMapper.selectByQuestionId(1);
        assertThat(answers.size(), is(2));
        System.out.println(answers);
    }

    @Test
    @Transactional
    public void selectByContentRegexp() {
        List<Answer> answers = answerMapper.selectByContentRegexp("answer content [12]");
        assertThat(answers.size(), is(2));
        System.out.println(answers);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        List<Answer> answers = answerMapper.selectByUserId(2);
        assertThat(answers.size(), is(1));
        System.out.println(answers);
    }
}