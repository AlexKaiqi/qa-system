package com.alex.qasystem.controller;

import com.alex.qasystem.entity.Tag;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.TagService;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TagController {
    private TagService tagService;
    private UserService userService;

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setUserMapper(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/tags/{tagId}")
    public Map<String, Object> getTagById(@PathVariable Integer tagId) {

        Map<String, Object> map = new HashMap<>(2);
        Tag tag = tagService.getTagById(tagId);
        if (tag == null) {
            map.put("success", false);
            map.put("message", "找不到该问题");
            return map;
        }
        map.put("success", true);
        map.put("tag", tag);
        return map;
    }

    @PostMapping("/tags")
    public Map<String, Object> addTag() {
        Map<String, Object> map = new HashMap<>(2);

        return null;
    }

    @DeleteMapping("/tags/{tagId}")
    public Map<String, Object> deleteTag(@PathVariable Integer tagId,
                                         @RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            tagService.deleteTag(user, tagId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PutMapping("/tags/{tagId}")
    public Map<String, Object> updateTag(@PathVariable Integer tagId,
                                         @RequestParam String description,
                                         @RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
            map.put("message", "需要验证身份");
            return map;
        }
        try {
            tagService.updateDescription(user, tagId, description);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
            return map;
        }
        map.put("success", true);
        return map;
    }

}
