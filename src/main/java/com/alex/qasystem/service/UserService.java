package com.alex.qasystem.service;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegisterExecution;
import com.alex.qasystem.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User getUserByEmailAndPassword(String email, String password);

    UserAuthExecution login(String email, String password);

    UserRegisterExecution register(String email, String profileName, String password);

}
