package com.alex.qasystem.controller;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.dto.TaggedQuestion;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionComment;
import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.*;
import java.util.regex.Pattern;

@RestController
public class QuestionController {

    private QuestionService questionService;
    private UserService userService;
    private TagMapper tagMapper;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setTagMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @GetMapping("/questions/{questionId}")
    public Map<String, Object> getQuestion(@PathVariable Integer questionId) {
        Map<String, Object> map = new HashMap<>(2);
        Question question = questionService.getQuestionById(questionId);
        if (question == null) {
            map.put("success", false);
            map.put("message", "找不到该问题");
            return map;
        }
        map.put("success", true);
        map.put("question", question);
        return map;
    }

    @PostMapping("/questions")
    public Map<String, Object> addQuestion(@RequestParam String title,
                                           @RequestParam String description,
                                           @RequestParam("tags[]") List<String> tags,
                                           @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        Question question;
        try {
            question = questionService.addQuestion(user, title, description, tags);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        map.put("question", question);
        return map;
    }

    @PostMapping("/questions/{questionId}/comment")
    public Map<String, Object> addQuestionComment(@PathVariable Integer questionId,
                                                  @RequestParam String content,
                                                  @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        QuestionComment questionComment;
        try {
            questionComment = questionService.addQuestionComment(user, questionId, content);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        map.put("questionComment", questionComment);
        return map;
    }

    @PostMapping("/questions/{questionId}/approval")
    public Map<String, Object> addQuestionApproval(@PathVariable Integer questionId,
                                                   @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            questionService.addQuestionApproval(user, questionId, 0);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PostMapping("/questions/{questionId}/disapproval")
    public Map<String, Object> addQuestionDisapproval(@PathVariable Integer questionId,
                                                      @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            questionService.addQuestionApproval(user, questionId, 1);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @DeleteMapping("/questions/{questionId}/comments/{commentId}")
    public Map<String, Object> deleteQuestionComment(@PathVariable Integer questionId,
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
            questionService.deleteQuestionComment(user, commentId);
        } catch (NotFoundException | AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        } catch(Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PutMapping("/questions/{questionId}")
    public Map<String, Object> modifyQuestion(@PathVariable Integer questionId,
                                              @RequestParam(required = false) String title,
                                              @RequestParam(value = "tags[]", required = false) List<String> tags,
                                              @RequestParam(required = false) String description,
                                              @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            questionService.updateQuestionContent(user, questionId, title, description, tags);
        } catch (NotFoundException | AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        } catch(Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;

    }

}
