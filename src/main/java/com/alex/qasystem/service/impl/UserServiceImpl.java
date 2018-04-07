package com.alex.qasystem.service.impl;

import com.alex.qasystem.dao.UserMapper;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userMapper.selectUserByUsernameAndPassword(username, password);
    }

    @Override
    public boolean registerUser(String username, String password) {

        return false;
    }


}
