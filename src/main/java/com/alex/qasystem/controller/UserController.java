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
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private QuestionService questionService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setQuestionService(QuestionService questionService) { this.questionService = questionService; }

    @PostMapping("/sign-in")
    @ResponseBody
    public UserAuthExecution signIn(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @GetMapping("sign-in")
    public String signIn() {
        return "login";
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public UserRegistrationExecution signUp(@RequestParam String email,
                                            @RequestParam String profileName,
                                            @RequestParam String password) {
        return userService.register(email, profileName, password);
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "login";
    }

    @PostMapping("/token")
    @ResponseBody
    public Map<String, Object> authToken(@RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(1);
        if (user == null) {
            map.put("success", false);
        } else {
            map.put("success", true);
        }
        return map;
    }

    @GetMapping("/account")
    public String account(@RequestParam String token,
                          ModelMap modelMap) {

        return "account";
    }
}
