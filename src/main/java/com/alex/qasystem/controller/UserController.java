package com.alex.qasystem.controller;

import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!"".equals(username) && !"".equals(password)) {
            User user = new User();
            user.setUsername(username);
            request.getSession().setAttribute("user", user);
            map.put("result", "1");
        } else {
            map.put("result", "0");
        }
        return map;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public Map<String, Object> register(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> map = new HashMap<>(2);
        if (!"".equals(username) && !"".equals(password)) {

        } else {

        }
        return map;
    }
}
