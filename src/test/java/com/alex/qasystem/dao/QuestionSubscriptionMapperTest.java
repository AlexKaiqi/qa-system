package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionSubscription;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionSubscriptionMapperTest {

    @Autowired
    private QuestionSubscriptionMapper questionSubscriptionMapper;

    @Test
    @Transactional
    public void insert() {
        QuestionSubscription questionSubscription = new QuestionSubscription();
        questionSubscription.setUserId(1);
        questionSubscription.setQuestionId(1);
        assertThat(questionSubscriptionMapper.insert(questionSubscription), is(1));
        assertThat(questionSubscriptionMapper.selectById(questionSubscription.getId()).toString(), is(questionSubscription.toString()));
        System.out.println(questionSubscription);
    }

    @Test
    @Transactional
    public void deleteById() {
        System.out.println(questionSubscriptionMapper.selectById(1));
        assertThat(questionSubscriptionMapper.deleteById(1), is(1));
        assertNull(questionSubscriptionMapper.selectById(1));
    }

    @Test
    @Transactional
    public void deleteByUserId() {
        System.out.println(questionSubscriptionMapper.selectByUserId(1));
        assertThat(questionSubscriptionMapper.deleteByUserId(1), is(2));
        assertThat(questionSubscriptionMapper.selectByUserId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void deleteByQuestionId() {
        System.out.println(questionSubscriptionMapper.selectByQuestionId(1));
        assertThat(questionSubscriptionMapper.deleteByQuestionId(1), is(1));
        assertThat(questionSubscriptionMapper.selectByQuestionId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void selectById() {
        QuestionSubscription questionSubscription = new QuestionSubscription();
        questionSubscription.setId(1);
        questionSubscription.setUserId(1);
        questionSubscription.setQuestionId(1);
        assertThat(questionSubscriptionMapper.selectById(1).toString(), is(questionSubscription.toString()));
        System.out.println(questionSubscription);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        assertThat(questionSubscriptionMapper.selectByUserId(1).size(), is(2));
        System.out.println(questionSubscriptionMapper.selectByUserId(1));
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        assertThat(questionSubscriptionMapper.selectByQuestionId(1).size(), is(1));
        System.out.println(questionSubscriptionMapper.selectByQuestionId(1));
    }
}