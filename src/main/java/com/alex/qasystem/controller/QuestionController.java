package com.alex.qasystem.controller;

import com.alex.qasystem.dao.TagMapper;
import com.alex.qasystem.dto.TaggedQuestion;
import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.QuestionComment;
import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.QuestionService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Pattern;

@Controller
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

    @PostMapping("/questions/ask")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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
}
