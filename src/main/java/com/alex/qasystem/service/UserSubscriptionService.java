package com.alex.qasystem.service;

import com.alex.qasystem.entity.*;

import java.util.List;

public interface UserSubscriptionService {

    List<User> getUserSubscriptions(Integer userId);


    UserSubscription addUserSubscription(Integer userId, Integer watchedUserId);

    UserSubscription deleteUserSubscriptionById(User user, Integer id);

}
