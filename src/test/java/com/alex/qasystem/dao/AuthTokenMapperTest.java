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
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenMapperTest {
    @Autowired
    AuthTokenMapper authTokenMapper;

    @Test
    @Transactional
    public void insert() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken("141a04dfe5a35469");
        authToken.setCreateTime(new Date());
        authToken.setExpireTime(new Date(new Date().getTime() + 7200000));
        assertThat(authTokenMapper.insert(authToken), is(1));
        System.out.println(authToken);
    }

    @Test
    @Transactional
    public void deleteExpiredToken() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken("141a04dfe5a35469");
        authToken.setCreateTime(new Date(new Date().getTime() - 7600000));
        authToken.setExpireTime(new Date(new Date().getTime() - 7200000));
        assertThat(authTokenMapper.insert(authToken), is(1));
        assertThat(authTokenMapper.deleteExpiredToken(), is(1));
    }

    @Test
    @Transactional
    public void updateExpireTimeByToken() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken("141a04dfe5a27469");
        authToken.setCreateTime(new Date());
        authToken.setExpireTime(new Date());
        assertThat(authTokenMapper.insert(authToken), is(1));
        Date newDate = new Date(new Date().getTime() + 960000);
        assertThat(authTokenMapper.updateExpireTimeByToken("141a04dfe5a27469", newDate), is(1));
        authToken = authTokenMapper.selectByToken("141a04dfe5a27469");
        System.out.println(authToken);
    }

    @Test
    @Transactional
    public void selectByToken() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken("141a04dfe5a27469");
        authToken.setCreateTime(new Date());
        authToken.setExpireTime(new Date(new Date().getTime() + 7200000));
        assertThat(authTokenMapper.insert(authToken), is(1));
        authToken = authTokenMapper.selectByToken("141a04dfe5a27469");
        assertNotNull(authToken);
        System.out.println(authToken);
    }

}