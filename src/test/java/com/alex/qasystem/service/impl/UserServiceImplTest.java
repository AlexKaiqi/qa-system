package com.alex.qasystem.service.impl;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserAuthStateEnum;
import com.alex.qasystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void getUserByEmailAndPassword() {
        User user = userService.getUserByEmailAndPassword("alexkai@gmail.com", "12345");
        assertThat(user.getId(), is(1));
        assertThat(user.getProfileName(), is("Alex Kai"));
    }

    @Test
    @Transactional
    public void login() {
        UserAuthExecution userAuthExecution;
        // ȱ����֤��Ϣ
        userAuthExecution = userService.login("alexkai@gmail.com", null);
        assertThat(userAuthExecution.getState(), is(UserAuthStateEnum.NULL_AUTH_INFO.getState()));
        userAuthExecution = userService.login(null, "");
        assertThat(userAuthExecution.getState(), is(UserAuthStateEnum.NULL_AUTH_INFO.getState()));

        // �û�����, �������
        userAuthExecution = userService.login("alexkai@gmail.com", "wrong-password");
        assertThat(userAuthExecution.getState(), is(UserAuthStateEnum.WRONG_PASSWORD.getState()));

        // �û�������
        userAuthExecution = userService.login("wrong-email@gmail.com", "12345");
        assertThat(userAuthExecution.getState(), is(UserAuthStateEnum.USER_NOT_EXISTS.getState()));

        // �������붼��ȷ
        userAuthExecution = userService.login("alexkai@gmail.com", "12345");
        assertThat(userAuthExecution.getState(), is(UserAuthStateEnum.SUCCESS.getState()));
    }


}