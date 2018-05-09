package com.alex.qasystem.controller;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegistrationExecution;
import com.alex.qasystem.entity.Bookmark;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.entity.UserSubscription;
import com.alex.qasystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/token/check")
    @ResponseBody
    public Map<String, Object> authToken(@RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
        } else {
            map.put("success", true);
        }
        return map;
    }

    @PostMapping("user/sign-in")
    @ResponseBody
    public UserAuthExecution signIn(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @PostMapping("/user/sign-up")
    @ResponseBody
    public UserRegistrationExecution signUp(@RequestParam String email,
                                            @RequestParam String profileName,
                                            @RequestParam String password) {
        return userService.register(email, profileName, password);
    }

    @PostMapping("/messages/userId")
    @ResponseBody Map<String, Object> readMessages() {
        return null;
    }

}
