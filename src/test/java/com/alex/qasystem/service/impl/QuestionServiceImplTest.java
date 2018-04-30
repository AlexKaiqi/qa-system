package com.alex.qasystem.service.impl;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserAuthStateEnum;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionServiceImplTest {

    @Autowired
    private QuestionService questionService;


    @Test
    @Transactional
    public void queryQuestionByTitleRegexp() {
        List<Question> questions = questionService.queryQuestionByTitleRegexp("When to");
        System.out.println(questions);
    }

    @Test
    @Transactional
    public void addQuestion() {
        String title = "a new question";
        List<String> tags = Arrays.asList("java", "spring", "mysql");
        String description = "some description";
        Integer userId = 2;
        System.out.println(questionService.addQuestion(userId, title, description, tags));
    }
}