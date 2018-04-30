package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.Question;
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
public class AnswerMapperTest {
    @Autowired
    private AnswerMapper answerMapper;

    @Test
    @Transactional
    public void insert() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(1);
        answer.setCreateTime(new Date());
        answer.setContent("test answer");
        answer.setStatus(0);
        answerMapper.insert(answer);
        answer = answerMapper.selectById(answer.getId());
        assertThat(answer.getContent(), is("test answer"));
        System.out.println(answer);
    }

    @Test
    @Transactional
    public void deleteById() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(1);
        answer.setCreateTime(new Date());
        answer.setContent("test answer");
        answer.setStatus(0);
        answerMapper.insert(answer);
        assertThat(answerMapper.deleteById(answer.getId()), is(1));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(2);
        answer.setCreateTime(new Date());
        answer.setContent("test answer");
        answer.setStatus(0);
        answerMapper.insert(answer);
        assertThat(answerMapper.deleteByQuestionId(2), is(1));
        assertThat(answerMapper.selectByQuestionId(2).size(), is(0));
    }

    @Test
    @Transactional
    public void updateById() {
        Answer answer = new Answer();
        answer.setUserId(2);
        answer.setQuestionId(2);
        answer.setCreateTime(new Date());
        answer.setContent("test answer");
        answer.setStatus(0);
        answerMapper.insert(answer);
        answer.setContent("new test content");
        assertThat(answerMapper.updateById(answer), is(1));
        answer = answerMapper.selectById(answer.getId());
        assertThat(answer.getContent(), is("new test content"));
        System.out.println(answer);
    }

    @Test
    @Transactional
    public void selectById() {
        Answer answer = answerMapper.selectById(1);
        assertNotNull(answer);
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
        List<Answer> answers = answerMapper.selectByContentRegexp("array|java");
        assertThat(answers.size(), is(2));
        System.out.println(answers);
    }
}