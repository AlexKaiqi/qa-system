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
    private static final long DATETIME = 1523788267000L;// 2018-04-15 18:31

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
        user.setRegisterTime(new Date(DATETIME));
        assertThat(userMapper.insert(user), is(1));
        assertThat(userMapper.selectById(user.getId()).toString(), is(user.toString()));
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
        user.setRegisterTime(new Date(DATETIME));
        assertThat(userMapper.insert(user), is(1));
        assertThat(userMapper.deleteById(user.getId()), is(1));
        assertNull(userMapper.selectById(user.getId()));
    }

    @Test
    @Transactional
    public void update() {
        System.out.println(userMapper.selectById(1));
        User user = userMapper.selectById(1);
        User updated = new User();
        updated.setId(1);
        updated.setEmail("test@gmail.com");
        updated.setProfileName("new");
        updated.setProfileImgSrc("/new/a.jpg");
        updated.setPassword("test-password");
        updated.setGroupId(1);
        updated.setStatus(1);
        updated.setReputation(10);
        updated.setRegisterTime(new Date(DATETIME));
        updated.setLastEditTime(new Date(DATETIME));
        updated.setLastLoginTime(new Date(DATETIME));
        updated = userMapper.selectById(1);
        assertThat(userMapper.updateById(updated), is(1));
        assertThat(user.toString(), is(updated.toString()));
        System.out.println(userMapper.selectById(1));
    }

    @Test
    @Transactional
    public void selectById() {
        User user = userMapper.selectById(1);
        User expect = new User();
        expect.setId(1);
        expect.setEmail("alexkai@gmail.com");
        expect.setPassword("$2a$10$2srVSi87gAZhoQXQoB.U2eqhrsCKuA0pGAu0RwvZhyRycayLyfpVm");
        expect.setProfileName("Alex Kai");
        expect.setProfileImgSrc("/images/avatars/avatar_default.png");
        expect.setGroupId(0);
        expect.setReputation(0);
        expect.setRegisterTime(new Date(DATETIME));
        expect.setStatus(0);
        assertThat(user.toString(), is(expect.toString()));

        System.out.println(user);
    }

    @Test
    @Transactional
    public void selectByEmail() {
        User user = userMapper.selectByEmail("alexkai@gmail.com");
        User expect = new User();
        expect.setId(1);
        expect.setEmail("alexkai@gmail.com");
        expect.setPassword("$2a$10$2srVSi87gAZhoQXQoB.U2eqhrsCKuA0pGAu0RwvZhyRycayLyfpVm");
        expect.setProfileName("Alex Kai");
        expect.setProfileImgSrc("/images/avatars/avatar_default.png");
        expect.setGroupId(0);
        expect.setReputation(0);
        expect.setRegisterTime(new Date(DATETIME));
        expect.setStatus(0);
        assertThat(user.toString(), is(expect.toString()));
        System.out.println(user);
    }

    @Test
    @Transactional
    public void selectSimpleById() {
        User user = userMapper.selectSimpleById(1);
        User expect = new User();
        expect.setId(1);
        expect.setProfileName("Alex Kai");
        expect.setProfileImgSrc("/images/avatars/avatar_default.png");
        expect.setReputation(0);

        assertThat(user.toString(), is(expect.toString()));
        System.out.println(user);
    }

}