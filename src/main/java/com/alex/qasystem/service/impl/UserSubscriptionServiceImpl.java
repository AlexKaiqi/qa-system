package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.UserMapper;
import com.alex.qasystem.dao.UserSubscriptionMapper;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.entity.UserSubscription;
import com.alex.qasystem.service.UserSubscriptionService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.ArrayList;
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
    public List<User> getSubscribedUserByUserId(Integer userId) {
        List<UserSubscription> subscriptions = userSubscriptionMapper.selectByUserId(userId);
        List<User> users = new ArrayList<>();
        for (UserSubscription subscription : subscriptions) {
            users.add(userMapper.selectById(subscription.getUserId()));
        }
        return users;
    }

    @Override
    public UserSubscription addUserSubscription(Integer userId, Integer watchedUserId) {

        UserSubscription subscription = new UserSubscription();
        subscription.setUserId(userId);
        subscription.setUserId(userId);
        userSubscriptionMapper.insert(subscription);
        return subscription;
    }

    @Override
    public UserSubscription deleteUserSubscriptionById(User user, Integer userSubscriptionId) throws NotFoundException, AuthException {
        UserSubscription subscription = userSubscriptionMapper.selectById(userSubscriptionId);
        if (subscription == null) {
            throw new NotFoundException("找不到该问题关注. userSubscriptionId: " + userSubscriptionId);
        }
        if (!subscription.getUserId().equals(user.getId())) {
            throw new AuthException("没有删除权限. userId: " + user.getId());
        }
        userSubscriptionMapper.deleteById(userSubscriptionId);
        return subscription;
    }


}
