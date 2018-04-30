package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionApproval;
import org.junit.Test;

import static org.junit.Assert.*;
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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionApprovalMapperTest {

    @Autowired
    private QuestionApprovalMapper questionApprovalMapper;

    @Test
    @Transactional
    public void insert() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(2);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date());
        questionApprovalMapper.insert(questionApproval);
        System.out.println(questionApprovalMapper.selectById(questionApproval.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(2);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date());
        questionApprovalMapper.insert(questionApproval);
        assertThat(questionApprovalMapper.deleteById(questionApproval.getId()), is(1));
    }

    @Test
    @Transactional
    public void updateById() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(2);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date());
        questionApprovalMapper.insert(questionApproval);
        questionApproval.setType(1);
        assertThat(questionApprovalMapper.updateById(questionApproval), is(1));
        System.out.println(questionApprovalMapper.selectById(questionApproval.getId()));
    }

    @Test
    @Transactional
    public void selectById() {
        QuestionApproval questionApproval = questionApprovalMapper.selectById(1);
        assertNotNull(questionApproval);
        System.out.println(questionApproval);
    }

    @Test
    @Transactional
    public void selectByQuestionId() {
        List<QuestionApproval> questionApprovals = questionApprovalMapper.selectByQuestionId(1);
        assertThat(questionApprovals.size(), is(4));
        System.out.println(questionApprovals);
    }

    @Test
    @Transactional
    public void countApprovalsByQuestionId() {
        int questionApprovals = questionApprovalMapper.countApprovalsByQuestionId(1);
        assertThat(questionApprovals, is(3));
    }

    @Test
    @Transactional
    public void countDisapprovalsByQuestionId() {
        int questionApprovals = questionApprovalMapper.countDisapprovalsByQuestionId(1);
        assertThat(questionApprovals, is(1));
    }
}