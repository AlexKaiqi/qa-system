package com.alex.qasystem.dao;

import com.alex.qasystem.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void insert() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setProfileName("Mr.Wang");
        user.setPassword("test-password");
        user.setGroupId(0);
        user.setStatus(0);
        user.setReputation(0);
        user.setRegisterTime(new Date());
        assertThat(userMapper.insert(user), is(1));
        System.out.println(userMapper.selectByEmail("test@gmail.com"));
    }

    @Test
    @Transactional
    public void deleteById() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user.setProfileName("Mr.Wang");
        user.setPassword("test-password");
        user.setGroupId(0);
        user.setStatus(0);
        user.setReputation(0);
        user.setRegisterTime(new Date());
        assertThat(userMapper.insert(user), is(1));
        assertThat(userMapper.deleteById(user.getId()), is(1));
        assertNull(userMapper.selectById(user.getId()));
    }

    @Test
    @Transactional
    public void selectByEmail() {
        User user = userMapper.selectByEmail("alexkai@gmail.com");
        assertThat(user.getProfileName(), is("Alex Kai"));
        System.out.println(user);
    }

    @Test
    @Transactional
    public void selectById() {
        User user = userMapper.selectById(1);
        assertThat(user.getProfileName(), is("Alex Kai"));
        System.out.println(user);
    }
}