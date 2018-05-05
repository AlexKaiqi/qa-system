package com.alex.qasystem.controller;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.AnswerService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AnswerController {

    private UserService userService;
    private AnswerService answerService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping("/questions/{questionId}/answer")
    @ResponseBody
    public Map<String, Object> addAnswer(@PathVariable Integer questionId,
                                         @RequestParam String content,
                                         @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        Answer answer;
        try {
            answer = answerService.addAnswer(user, questionId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        map.put("answer", answer);
        return map;
    }

    @PostMapping("/answers/{answerId}/comment")
    @ResponseBody
    public Map<String, Object> addAnswerComment(@PathVariable Integer answerId,
                                                @RequestParam String content,
                                                @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        AnswerComment answerComment;
        try {
            answerComment = answerService.addAnswerComment(user, answerId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        map.put("answerComment", answerComment);
        return map;
    }

    @PostMapping("/answers/{answerId}/approval")
    @ResponseBody
    public Map<String, Object> addAnswerApproval(@PathVariable Integer answerId,
                                                 @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        answerService.addAnswerApproval(user, answerId, 0);
        map.put("success", true);
        return map;
    }

    @PostMapping("answers/{answerId}/disapproval")
    @ResponseBody
    public Map<String, Object> addAnswerDisapproval(@PathVariable Integer answerId,
                                                    @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        answerService.addAnswerApproval(user, answerId, 1);
        map.put("success", true);
        return map;
    }


}
