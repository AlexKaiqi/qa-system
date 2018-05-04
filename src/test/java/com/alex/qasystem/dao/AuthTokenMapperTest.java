package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AuthToken;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenMapperTest {
    @Autowired
    AuthTokenMapper authTokenMapper;
    private static final long DATETIME = 1505321390000L;// 2017-09-14 00:49
    private static final long TWO_HOURS = 7200000; // 2 hours
    private static final String TOKEN = "141a04dfe5a35469";

    @Test
    @Transactional
    public void insert() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(TOKEN);
        authToken.setCreateTime(new Date(DATETIME));
        authToken.setExpireTime(new Date(DATETIME + TWO_HOURS));
        assertThat(authTokenMapper.insert(authToken), is(1));
        System.out.println(authToken);
    }

    @Test
    @Transactional
    public void deleteExpiredToken() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(TOKEN);
        authToken.setCreateTime(new Date(DATETIME));
        authToken.setExpireTime(new Date(DATETIME + TWO_HOURS));
        System.out.println(authToken);
        assertThat(authTokenMapper.insert(authToken), is(1));
        assertThat(authTokenMapper.deleteExpiredToken(), is(1));
        assertNull(authTokenMapper.selectByToken(TOKEN));
    }

    @Test
    @Transactional
    public void updateExpireTimeByToken() {

        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(TOKEN);
        authToken.setCreateTime(new Date(DATETIME));
        authToken.setExpireTime(new Date(DATETIME + TWO_HOURS));
        System.out.println(authToken);
        assertThat(authTokenMapper.updateExpireTimeByToken(TOKEN, new Date(DATETIME)), is(1));
        AuthToken updated = authTokenMapper.selectByToken(TOKEN);
        assertThat(updated.getExpireTime().toString(), is(new Date(DATETIME).toString()));
        System.out.println(authTokenMapper.selectByToken(TOKEN));

    }

    @Test
    @Transactional
    public void selectByToken() {
        AuthToken authToken = new AuthToken();
        authToken.setId(1);
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(TOKEN);
        authToken.setCreateTime(new Date(DATETIME));
        authToken.setExpireTime(new Date(DATETIME + TWO_HOURS));
        assertThat(authTokenMapper.selectByToken(TOKEN).toString(), is(authToken.toString()));
        System.out.println(authToken);
    }

    @Test
    @Transactional
    public void selectByUserId() {
        AuthToken authToken = new AuthToken();
        authToken.setId(1);
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(TOKEN);
        authToken.setCreateTime(new Date(DATETIME));
        authToken.setExpireTime(new Date(DATETIME + TWO_HOURS));
        assertThat(authTokenMapper.selectByUserId(1).toString(), is(authToken.toString()));
        System.out.println(authToken);
    }
}