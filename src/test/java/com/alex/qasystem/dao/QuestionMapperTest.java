package com.alex.qasystem.dao;

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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionMapperTest {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49


    @Test
    @Transactional
    public void insert() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Question question = new Question();
        question.setTitle("test question");
        question.setDescription("test description");
        question.setStatus(0);
        question.setUserId(1);
        question.setCreateTime(new Date(DATETIME));
        question.setLastEditTime(new Date(DATETIME));
        assertThat(questionMapper.insert(question), is(1));
        Question inserted = questionMapper.selectById(question.getId());
        assertThat(inserted.getTitle(), is(question.getTitle()));
        assertThat(inserted.getDescription(), is(question.getDescription()));
        assertThat(inserted.getStatus(), is(question.getStatus()));
        assertThat(inserted.getUserId(), is(question.getUserId()));
        assertThat(f.format(inserted.getCreateTime()), is(f.format(question.getCreateTime())));
        assertThat(f.format(inserted.getLastEditTime()), is(f.format(question.getLastEditTime())));
        System.out.println(question);
    }

    @Test
    @Transactional
    public void deleteById() {
        Question question = new Question();
        question.setTitle("test question");
        question.setDescription("test description");
        question.setStatus(0);
        question.setUserId(1);
        question.setCreateTime(new Date(DATETIME));
        question.setLastEditTime(new Date(DATETIME));
        assertThat(questionMapper.insert(question), is(1));
        assertThat(questionMapper.deleteById(question.getId()), is(1));
        assertNull(questionMapper.selectById(question.getId()));
        System.out.println(question);
    }

    @Test
    @Transactional
    public void deleteTagReferencesByQuestionId() {
        System.out.println(questionMapper.selectById(1));
        assertThat(questionMapper.deleteTagReferencesByQuestionId(1), is(2));
        assertThat(questionMapper.selectById(1).getTags().size(), is(0));
    }

    @Test
    @Transactional
    public void updateById() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Question updated = new Question();
        updated.setId(1);
        updated.setTitle("test question");
        updated.setDescription("test description");
        updated.setStatus(0);
        updated.setUserId(1);
        updated.setCreateTime(new Date(DATETIME));
        updated.setLastEditTime(new Date(DATETIME));
        assertThat(questionMapper.updateById(updated), is(1));
        Question question = questionMapper.selectById(updated.getId());
        assertThat(question.getTitle(), is(updated.getTitle()));
        assertThat(question.getDescription(), is(updated.getDescription()));
        assertThat(question.getStatus(), is(updated.getStatus()));
        assertThat(question.getUserId(), is(updated.getUserId()));
        assertThat(f.format(question.getCreateTime()), is(f.format(updated.getCreateTime())));
        assertThat(f.format(question.getLastEditTime()), is(f.format(updated.getLastEditTime())));
        System.out.println(questionMapper.selectById(1));
    }

    @Test
    @Transactional
    public void selectById() {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Question question = questionMapper.selectById(1);
        assertThat(question.getTitle(), is("When to use LinkedList over ArrayList?"));
        assertThat(question.getDescription(), is("test description 1"));
        assertThat(question.getStatus(), is(0));
        assertThat(question.getUserId(), is(question.getUserId()));
        assertThat(f.format(question.getCreateTime()), is("2017-09-14 00:49"));
        assertNull(question.getLastEditTime());
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

    @Test
    @Transactional
    public void selectByUserId() {
        assertThat(questionMapper.selectByUserId(1).size(), is(1));
        System.out.println(questionMapper.selectByUserId(1));
    }
}