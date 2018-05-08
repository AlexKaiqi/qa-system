package com.alex.qasystem.dao;

import com.alex.qasystem.entity.UserSubscription;
import org.apache.ibatis.annotations.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSubscriptionMapperTest {

    @Autowired
    private UserSubscriptionMapper userSubscriptionMapper;
    
    @Test
    @Transactional
    public void insert() {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserId(1);
        userSubscription.setWatchedUserId(3);
        assertThat(userSubscriptionMapper.insert(userSubscription), is(1));
        assertThat(userSubscriptionMapper.selectById(userSubscription.getId()).toString(), is(userSubscription.toString()));
        System.out.println(userSubscription);
    }

    @Test
    @Transactional
    public void deleteById() {
        System.out.println(userSubscriptionMapper.selectById(1));
        assertThat(userSubscriptionMapper.deleteById(1), is(1));
        assertNull(userSubscriptionMapper.selectById(1));
    }

    @Test
    @Transactional
    public void deleteByUserId() {
        System.out.println(userSubscriptionMapper.selectByUserId(1));
        assertThat(userSubscriptionMapper.deleteByUserId(1), is(2));
        assertThat(userSubscriptionMapper.selectByUserId(1).size(), is(0));
    }

    @Test
    @Transactional
    public void selectById() {
        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setId(1);
        userSubscription.setUserId(1);
        userSubscription.setWatchedUserId(2);
        assertThat(userSubscriptionMapper.selectById(1).toString(), is(userSubscription.toString()));
        System.out.println(userSubscription);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        assertThat(userSubscriptionMapper.selectByUserId(1).size(), is(2));
        System.out.println(userSubscriptionMapper.selectByUserId(1));
    }

    @Test
    @Transactional
    public void selectByWatchedUserId() {
        assertThat(userSubscriptionMapper.selectByWatchedUserId(2).size(), is(1));
        System.out.println(userSubscriptionMapper.selectByWatchedUserId(2));
    }
}