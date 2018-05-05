package com.alex.qasystem.controller;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegistrationExecution;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alex
 */
@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/sign-in")
    public String signIn() {
        return "login";
    }

    @GetMapping("/user/sign-up")
    public String signUp() {
        return "login";
    }

    @GetMapping("/user/account")
    public String account(@RequestParam String token,
                          ModelMap modelMap) {

        return "account";
    }


}
