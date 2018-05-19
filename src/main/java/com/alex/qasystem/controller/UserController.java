package com.alex.qasystem.controller;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegistrationExecution;
import com.alex.qasystem.entity.MedalRecord;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.MedalService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private UserService userService;
    private MedalService medalService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMedalService(MedalService medalService) {this.medalService = medalService;}
    @GetMapping("/token/check")
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
    public UserAuthExecution signIn(@RequestParam String email, @RequestParam String password) {
        return userService.login(email, password);
    }

    @PostMapping("/user/sign-up")
    public UserRegistrationExecution signUp(@RequestParam String email,
                                            @RequestParam String profileName,
                                            @RequestParam String password) {
        return userService.register(email, profileName, password);
    }

    @PostMapping("/users/{userId}")
    public Map<String, Object> uploadProfileImg(@PathVariable Integer userId,
                                                @RequestParam MultipartFile profileImg,
                                                @RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        try {
            userService.updateProfileImg(user, profileImg);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "�ϴ�ʧ��");
        }
        map.put("success", true);
        return map;
    }

    @PutMapping("/users/{userId}")
    public Map<String, Object> changePwd(@PathVariable Integer userId,
                                         @RequestParam String oldPassword,
                                         @RequestParam String newPassword,
                                         @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        try {
            map = userService.changePassword(user, oldPassword, newPassword);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "����ʧ��");
        }
        return map;
    }

    @GetMapping("/users/{userId}/medals")
    public Map<String, Object> getMedals(@PathVariable Integer userId,
                                         @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        List<MedalRecord> medalRecords = medalService.getMedalsByUserId(user.getId());
        map.put("success", true);
        map.put("medalRecords", medalRecords);
        return map;
    }
}
