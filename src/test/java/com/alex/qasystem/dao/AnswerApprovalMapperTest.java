package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AnswerApproval;
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
public class AnswerApprovalMapperTest {
    @Autowired
    private AnswerApprovalMapper answerApprovalMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(2);
        answerApproval.setAnswerId(1);
        answerApproval.setType(0);
        answerApproval.setCreateTime(new Date(DATETIME));
        assertThat(answerApprovalMapper.insert(answerApproval), is(1));
        assertThat(answerApprovalMapper.selectById(answerApproval.getId()).toString(), is(answerApproval.toString()));
        System.out.println(answerApprovalMapper.selectById(answerApproval.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(1);
        answerApproval.setAnswerId(1);
        answerApproval.setType(0);
        answerApproval.setCreateTime(new Date(DATETIME));
        assertThat(answerApprovalMapper.insert(answerApproval), is(1));
        assertThat(answerApprovalMapper.deleteById(answerApproval.getId()), is(1));
        assertNull(answerApprovalMapper.selectById(answerApproval.getId()));
        System.out.println(answerApproval);
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(answerApprovalMapper.selectById(1));
        AnswerApproval updated = new AnswerApproval();
        updated.setId(1);
        updated.setUserId(1);
        updated.setAnswerId(2);
        updated.setType(0);
        updated.setCreateTime(new Date(DATETIME));
        assertThat(answerApprovalMapper.updateById(updated), is(1));
        AnswerApproval answerApproval = answerApprovalMapper.selectById(1);
        assertThat(answerApproval.toString(), is(updated.toString()));
        System.out.println(updated);
    }

    @Test
    @Transactional
    public void selectById() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setId(1);
        answerApproval.setUserId(2);
        answerApproval.setAnswerId(1);
        answerApproval.setType(1);
        answerApproval.setCreateTime(new Date(DATETIME));
        assertThat(answerApprovalMapper.selectById(1).toString(), is(answerApproval.toString()));
        System.out.println(answerApproval);
    }

    @Test
    @Transactional
    public void selectByAnswerId() {
        List<AnswerApproval> answerApprovals = answerApprovalMapper.selectByAnswerId(1);
        assertThat(answerApprovals.size(), is(3));
        System.out.println(answerApprovals);
    }

    @Test
    @Transactional
    public void countApprovalsByAnswerId() {
        int answerApprovals = answerApprovalMapper.countApprovalsByAnswerId(1);
        assertThat(answerApprovals, is(2));
        System.out.println(answerApprovalMapper.selectByAnswerId(1));
    }

    @Test
    @Transactional
    public void countDisapprovalsByAnswerId() {
        int answerApprovals = answerApprovalMapper.countDisapprovalsByAnswerId(1);
        assertThat(answerApprovals, is(1));
        System.out.println(answerApprovalMapper.selectByAnswerId(1));
    }
}