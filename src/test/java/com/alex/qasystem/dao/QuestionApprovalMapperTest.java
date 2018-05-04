package com.alex.qasystem.dao;

import com.alex.qasystem.entity.QuestionApproval;
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
public class QuestionApprovalMapperTest {

    @Autowired
    private QuestionApprovalMapper questionApprovalMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49


    @Test
    @Transactional
    public void insert() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(2);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date(DATETIME));
        assertThat(questionApprovalMapper.insert(questionApproval), is(1));
        assertThat(questionApprovalMapper.selectById(questionApproval.getId()).toString(), is(questionApproval.toString()));
        System.out.println(questionApprovalMapper.selectById(questionApproval.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setUserId(1);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date(DATETIME));
        assertThat(questionApprovalMapper.insert(questionApproval), is(1));
        assertThat(questionApprovalMapper.deleteById(questionApproval.getId()), is(1));
        assertNull(questionApprovalMapper.selectById(questionApproval.getId()));
        System.out.println(questionApproval);
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(questionApprovalMapper.selectById(1));
        QuestionApproval updated = new QuestionApproval();
        updated.setId(1);
        updated.setUserId(2);
        updated.setQuestionId(2);
        updated.setType(1);
        updated.setCreateTime(new Date(DATETIME));
        assertThat(questionApprovalMapper.updateById(updated), is(1));
        QuestionApproval questionApproval = questionApprovalMapper.selectById(1);
        assertThat(questionApproval.toString(), is(updated.toString()));
        System.out.println(updated);
    }

    @Test
    @Transactional
    public void selectById() {
        QuestionApproval questionApproval = new QuestionApproval();
        questionApproval.setId(1);
        questionApproval.setUserId(2);
        questionApproval.setQuestionId(1);
        questionApproval.setType(0);
        questionApproval.setCreateTime(new Date(DATETIME));
        assertThat(questionApprovalMapper.selectById(1).toString(), is(questionApproval.toString()));
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
        System.out.println(questionApprovalMapper.selectByQuestionId(1));
    }

    @Test
    @Transactional
    public void countDisapprovalsByQuestionId() {
        int questionApprovals = questionApprovalMapper.countDisapprovalsByQuestionId(1);
        assertThat(questionApprovals, is(1));
        System.out.println(questionApprovalMapper.selectByQuestionId(1));
    }
}