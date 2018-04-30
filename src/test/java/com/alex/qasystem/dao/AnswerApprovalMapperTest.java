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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerApprovalMapperTest {
    @Autowired
    private AnswerApprovalMapper answerApprovalMapper;

    @Test
    @Transactional
    public void insert() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(2);
        answerApproval.setAnswerId(1);
        answerApproval.setType(0);
        answerApproval.setCreateTime(new Date());
        answerApprovalMapper.insert(answerApproval);
        System.out.println(answerApprovalMapper.selectById(answerApproval.getId()));
    }

    @Test
    @Transactional
    public void deleteById() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(2);
        answerApproval.setAnswerId(1);
        answerApproval.setType(0);
        answerApproval.setCreateTime(new Date());
        answerApprovalMapper.insert(answerApproval);
        assertThat(answerApprovalMapper.deleteById(answerApproval.getId()), is(1));
    }

    @Test
    @Transactional
    public void updateById() {
        AnswerApproval answerApproval = new AnswerApproval();
        answerApproval.setUserId(2);
        answerApproval.setAnswerId(1);
        answerApproval.setType(0);
        answerApproval.setCreateTime(new Date());
        answerApprovalMapper.insert(answerApproval);
        answerApproval.setType(1);
        assertThat(answerApprovalMapper.updateById(answerApproval), is(1));
        System.out.println(answerApprovalMapper.selectById(answerApproval.getId()));
    }

    @Test
    @Transactional
    public void selectById() {
        AnswerApproval answerApproval = answerApprovalMapper.selectById(1);
        assertNotNull(answerApproval);
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
    }

    @Test
    @Transactional
    public void countDisapprovalsByAnswerId() {
        int answerApprovals = answerApprovalMapper.countDisapprovalsByAnswerId(1);
        assertThat(answerApprovals, is(1));
    }
}