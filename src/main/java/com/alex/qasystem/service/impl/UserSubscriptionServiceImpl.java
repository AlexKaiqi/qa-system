package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.UserMapper;
import com.alex.qasystem.dao.UserSubscriptionMapper;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.entity.UserSubscription;
import com.alex.qasystem.service.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    private UserSubscriptionMapper userSubscriptionMapper;
    private UserMapper userMapper;

    @Autowired
    public void setUserSubscriptionMapper(UserSubscriptionMapper userSubscriptionMapper) {
        this.userSubscriptionMapper = userSubscriptionMapper;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getUserSubscriptions(Integer userId) {
        return null;
    }

    @Override
    public UserSubscription addUserSubscription(Integer userId, Integer watchedUserId) {
        return null;
    }

    @Override
    public UserSubscription deleteUserSubscriptionById(User user, Integer id) {
        return null;
    }


}
