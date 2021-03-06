package com.alex.qasystem.controller;

import com.alex.qasystem.entity.Question;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.BookmarkService;
import com.alex.qasystem.service.QuestionSubscriptionService;
import com.alex.qasystem.service.UserService;
import com.alex.qasystem.service.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SubscriptionController {

    private QuestionSubscriptionService questionSubscriptionService;
    private UserSubscriptionService userSubscriptionService;
    private BookmarkService bookmarkService;
    private UserService userService;

    @Autowired
    public void setQuestionSubscriptionService(QuestionSubscriptionService questionSubscriptionService) {
        this.questionSubscriptionService = questionSubscriptionService;
    }

    @Autowired
    public void setUserSubscriptionService(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @Autowired
    public void setBookmarkService(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/question-subscriptions")
    public Map<String, Object> getQuestionSubscriptions(@PathVariable Integer userId,
                                                        @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null ) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        List<Question> subscribedQuestions = questionSubscriptionService.getSubscribedQuestionsByUserId(user.getId());
        map.put("success", true);
        map.put("subscribedQuestions", subscribedQuestions);
        return map;
    }

    @GetMapping("/users/{userId}/user-subscriptions")
    public Map<String, Object> getUserSubscriptions(@PathVariable Integer userId,
                                                        @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null ) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        List<User> subscribedUsers = userSubscriptionService.getSubscribedUserByUserId(user.getId());
        map.put("success", true);
        map.put("subscribedUsers", subscribedUsers);
        return map;
    }

    @GetMapping("/users/{userId}/bookmarks")
    public Map<String, Object> getBookmarks(@PathVariable Integer userId,
                                                        @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null ) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        List<Question> bookmarks = bookmarkService.selectBookmarkedQuestionByUserId(user.getId());
        map.put("success", true);
        map.put("bookmarks", bookmarks);
        return map;
    }

    @PostMapping("/users/{userId}/question-subscriptions/{questionId}")
    public Map<String, Object> addQuestionSubscription(@PathVariable Integer userId,
                                                       @PathVariable Integer questionId,
                                                       @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            questionSubscriptionService.addQuestionSubscription(user, questionId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @DeleteMapping("/users/{userId}/question-subscriptions/{questionId}")
    public Map<String, Object> deleteQuestionSubscription(@PathVariable Integer userId,
                                                          @PathVariable Integer questionId,
                                                          @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            questionSubscriptionService.deleteByUserIdAndQuestionId(user, questionId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PostMapping("/users/{userId}/user-subscriptions/{watchedUserId}")
    public Map<String, Object> addUserSubscription(@PathVariable Integer userId,
                                                   @PathVariable Integer watchedUserId,
                                                   @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            userSubscriptionService.addUserSubscription(user, watchedUserId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @DeleteMapping("/users/{userId}/user-subscriptions/{watchedUserId}")
    public Map<String, Object> deleteUserSubscription(@PathVariable Integer userId,
                                                      @PathVariable Integer watchedUserId,
                                                      @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            userSubscriptionService.deleteByUserIdAndWatchedUserId(user, watchedUserId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PostMapping("/users/{userId}/bookmarks/{questionId}")
    public Map<String, Object> addBookmark(@PathVariable Integer userId,
                                           @PathVariable Integer questionId,
                                           @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            bookmarkService.addBookmark(user, questionId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @DeleteMapping("/users/{userId}/bookmarks/{questionId}")
    public Map<String, Object> deleteBookmark(@PathVariable Integer userId,
                                              @PathVariable Integer questionId,
                                              @RequestParam String token) {
        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            bookmarkService.deleteBookmarkByQuestionId(user, questionId);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
        }
        map.put("success", true);
        return map;
    }

}
