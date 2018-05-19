package com.alex.qasystem.controller;

import com.alex.qasystem.enums.UserAuthStateEnum;
import com.alex.qasystem.enums.UserRegistrationStateEnum;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setupMockMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build(); //初始化MockMvc对象
        session = new MockHttpSession();
    }

    @Test
    @Transactional
    public void login() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/sign-in")
                .param("email", "alexkai@gmail.com")
                .param("password", "12345")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.stateInfo").value(UserAuthStateEnum.SUCCESS.getStateInfo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.profileName").value("Alex Kai"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public void signUp() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/user/sign-up")
                .param("email", "test@gmail.com")
                .param("password", "")
                .param("profileName", "王晓红")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .session(session)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.stateInfo").value(UserRegistrationStateEnum.SUCCESS.getStateInfo()))
                .andDo(MockMvcResultHandlers.print());
    }


}