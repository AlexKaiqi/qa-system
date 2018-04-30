package com.alex.qasystem.dao;

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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;

    @Test
    @Transactional
    public void insert() {
        Question question = new Question();
        question.setTitle("test question");
        question.setDescription("test description");
        question.setStatus(0);
        question.setUserId(1);
        question.setCreateTime(new Date());
        question.setLastEditTime(new Date());
        assertThat(questionMapper.insert(question), is(1));
        System.out.println(questionMapper.selectById(question.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        Question question = new Question();
        question.setTitle("test question");
        question.setDescription("test description");
        question.setStatus(0);
        question.setUserId(1);
        question.setCreateTime(new Date());
        question.setLastEditTime(new Date());
        assertThat(questionMapper.insert(question), is(1));
        assertThat(questionMapper.deleteById(question.getId()), is(1));
        assertNull(questionMapper.selectById(question.getId()));
    }

    @Test
    @Transactional
    public void updateById() {
        Question question = new Question();
        question.setId(1);
        question.setTitle("test");
        assertThat(questionMapper.updateById(question), is(1));
        assertThat(questionMapper.selectById(1).getTitle(), is("test"));
        System.out.println(questionMapper.selectById(1));
    }

    @Test
    @Transactional
    public void selectById() {
        Question question = questionMapper.selectById(1);
        assertThat(question.getTitle(), is("When to use LinkedList over ArrayList?"));
        System.out.println(question);
    }

    @Test
    @Transactional
    public void selectByTitleRegexp() {
        List<Question> questions = questionMapper.selectByTitleRegexp("When to use");
        assertThat(questions.size(), is(1));
        System.out.println(questions);
    }

    @Test
    @Transactional
    public void selectByTagRegexp() {
        List<Question> questions = questionMapper.selectByTagRegexp("javascript|java|spring");
        assertThat(questions.size(), is(1));
        System.out.println(questions);
    }
}