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
            users.add(userMapper.selectSimpleById(subscription.getWatchedUserId()));
        }
        return users;
    }

    @Override
    public UserSubscription addUserSubscription(User user, Integer watchedUserId) {
        // 检查是否已经收藏, 如果有直接返回.
        List<UserSubscription> subscriptions = userSubscriptionMapper.selectByUserId(user.getId());
        for (UserSubscription subscription : subscriptions) {
            if (subscription.getWatchedUserId().equals(watchedUserId)) {
                return subscription;
            }
        }
        UserSubscription subscription = new UserSubscription();
        subscription.setUserId(user.getId());
        subscription.setWatchedUserId(watchedUserId);
        userSubscriptionMapper.insert(subscription);
        return subscription;
    }

    @Override
    public UserSubscription deleteByUserIdAndWatchedUserId(User user, Integer watchedUserId) throws NotFoundException, AuthException {
        UserSubscription subscription = userSubscriptionMapper.selectByUserIdAndWatchedUserId(user.getId(), watchedUserId);
        if (subscription == null) {
            throw new NotFoundException("找不到该问题关注. watchedUserId: " + watchedUserId);
        }
        if (!subscription.getUserId().equals(user.getId())) {
            throw new AuthException("没有删除权限. userId: " + user.getId());
        }
        userSubscriptionMapper.deleteById(subscription.getId());
        return subscription;
    }
}
