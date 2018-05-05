package com.alex.qasystem.dao;

import com.alex.qasystem.entity.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageMapperTest {
    @Autowired
    private MessageMapper messageMapper;

    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49

    @Test
    @Transactional
    public void insert() {
        Message message = new Message();
        message.setType(1);
        message.setContent("test content");
        message.setReceiverId(1);
        message.setSendTime(new Date(DATETIME));
        message.setReceiveTime(new Date(DATETIME));
        message.setStatus(1);
        assertThat(messageMapper.insert(message), is(1));
        assertThat(messageMapper.selectById(message.getId()).toString(), is(message.toString()));
        System.out.println(message);
    }

    @Test
    @Transactional
    public void deleteById() {
        System.out.println(messageMapper.selectById(1));
        assertThat(messageMapper.deleteById(1), is(1));
        assertNull(messageMapper.selectById(1));
    }

    @Test
    @Transactional
    public void updateById() {
        System.out.println(messageMapper.selectById(1));
        Message updated = new Message();
        updated.setId(1);
        updated.setType(1);
        updated.setContent("test content");
        updated.setReceiverId(1);
        updated.setSendTime(new Date(DATETIME));
        updated.setReceiveTime(new Date(DATETIME));
        updated.setStatus(1);
        assertThat(messageMapper.updateById(updated), is(1));
        assertThat(messageMapper.selectById(1).toString(), is(updated.toString()));
    }

    @Test
    @Transactional
    public void selectById() {
        Message message = messageMapper.selectById(1);
        assertThat(message.getReceiverId(), is(1));
        assertThat(message.getType(), is(0));
        assertThat(message.getContent(), is("问题有了<a href=\"/questions/1\">新回答</a>"));
        assertThat(message.getStatus(), is(0));
        System.out.println(message);
    }

    @Test
    @Transactional
    public void selectByReceiverId() {
        List<Message> messages = messageMapper.selectByReceiverId(1);
        assertThat(messages.size(), is(3));
        System.out.println(messages);
    }
}