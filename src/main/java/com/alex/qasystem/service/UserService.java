package com.alex.qasystem.service;

import com.alex.qasystem.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByUsernameAndPassword(String username, String password);

    boolean registerUser(String username, String password);
}
