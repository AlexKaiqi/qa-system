package com.alex.qasystem.controller;

import com.alex.qasystem.entity.User;
import com.alex.qasystem.entity.UserSubscription;
import com.alex.qasystem.service.MessageService;
import com.alex.qasystem.service.UserService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    private MessageService messageService;
    private UserService userService;

    @Autowired
    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/messages/{messageId}")
    public Map<String, Object> deleteMessage(@PathVariable Integer messageId,
                                             @RequestParam String token) {

        Map<String, Object> map = new HashMap<>(2);
        User user = userService.getUserIdByToken(token);
        if (user == null) {
            map.put("success", false);
            map.put("message", "��Ҫ��֤���");
            return map;
        }
        try {
            messageService.deleteMessageById(user, messageId);
        } catch (NotFoundException |AuthException e) {
            map.put("success", false);
            map.put("message", e.getMessage());
            return map;
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
