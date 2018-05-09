package com.alex.qasystem.service;

import com.alex.qasystem.entity.User;
import com.alex.qasystem.entity.UserSubscription;
import org.apache.ibatis.javassist.NotFoundException;

import javax.security.auth.message.AuthException;
import java.util.List;

public interface UserSubscriptionService {

    List<User> getSubscribedUserByUserId(Integer userId);

    UserSubscription addUserSubscription(User user, Integer watchedUserId);

    UserSubscription deleteByUserIdAndWatchedUserId(User user, Integer watchedUserId) throws NotFoundException, AuthException;

}
