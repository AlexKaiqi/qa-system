package com.alex.qasystem.controller;

import com.alex.qasystem.entity.Answer;
import com.alex.qasystem.entity.AnswerComment;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.AnswerService;
import com.alex.qasystem.service.UserService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@RestController
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

    @PostMapping("/questions/{questionId}/answers/{answerId}/comment")
    public Map<String, Object> addAnswerComment(@PathVariable Integer questionId,
                                                @PathVariable Integer answerId,
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

    @PostMapping("/questions/{questionId}/answers/{answerId}/approval")
    public Map<String, Object> addAnswerApproval(@PathVariable Integer questionId,
                                                 @PathVariable Integer answerId,
                                                 @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            answerService.addAnswerApproval(user, answerId, 0);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PostMapping("/questions/{questionId}/answers/{answerId}/disapproval")
    public Map<String, Object> addAnswerDisapproval(@PathVariable Integer questionId,
                                                    @PathVariable Integer answerId,
                                                    @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "authentication token required");
            return map;
        }
        try {
            answerService.addAnswerApproval(user, answerId, 1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PutMapping("/questions/{questionId}/answers/{answerId}")
    public Map<String, Object> modifyAnswer(@PathVariable Integer questionId,
                                            @PathVariable Integer answerId,
                                            @RequestParam String content,
                                            @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            answerService.updateAnswerContent(user, answerId, content);
        } catch (NotFoundException | AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;

    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}")
    public Map<String, Object> deleteAnswer(@PathVariable Integer questionId,
                                            @PathVariable Integer answerId,
                                            @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            answerService.deleteAnswer(user, answerId);
        } catch (NotFoundException | AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @DeleteMapping("/questions/{questionId}/answers/{answerId}/comments/{commentId}")
    public Map<String, Object> deleteAnswerComment(@PathVariable Integer questionId,
                                                   @PathVariable Integer answerId,
                                                   @PathVariable Integer commentId,
                                                   @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            answerService.deleteAnswerComment(user, commentId);
        } catch (NotFoundException | AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }
}
