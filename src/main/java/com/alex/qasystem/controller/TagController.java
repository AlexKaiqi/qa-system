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

@Controller
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
        try {

        } catch (Exception e) {

        }
        return null;
    }

    @PutMapping("/tags/{tagId}")
    public Map<String, Object> updateTag(@PathVariable Integer tagId,
                                         @RequestParam String description,
                                         @RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        try {
            tagService.updateDescription(user, tagId, description);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "����ʧ��");
            return map;
        }
        map.put("success", true);
        return map;
    }

    @PostMapping("/tags")
    public Map<String, Object> addTag() {
        return null;
    }

    @DeleteMapping("/tags/{tagId}")
    public Map<String, Object> deleteTag(@PathVariable Integer tagId,
                                         @RequestParam String token) {
        User user = userService.getUserIdByToken(token);
        Map<String, Object> map = new HashMap<>(2);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        try {
            tagService.deleteTag(user, tagId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "����ʧ��");
            return map;
        }
        map.put("success", true);
        return map;
    }
}
