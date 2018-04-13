package com.alex.qasystem.dao;

import com.alex.qasystem.entity.AuthToken;
import com.alex.qasystem.util.SecurityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTokenMapperTest {
    @Autowired
    AuthTokenMapper authTokenMapper;

    @Test
    public void insert() {
        AuthToken authToken = new AuthToken();
        authToken.setUserId(1);
        authToken.setUserGroupId(0);
        authToken.setToken(SecurityUtil.generateToken());
        authToken.setCreateTime(new Date());
        authToken.setExpireTime(new Date(new Date().getTime() + 7200000));
        authTokenMapper.insert(authToken);
        System.out.println(authToken);
    }

    @Test
    public void selectByToken() {
        insert();
        AuthToken authToken = authTokenMapper.selectByToken("141a04dfe5a27469");
        assertThat(authToken.getId(), is(1));
        assertThat(authToken.getUserGroupId(), is(0));
    }
}