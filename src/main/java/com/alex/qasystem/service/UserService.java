package com.alex.qasystem.service;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.dto.UserRegistrationExecution;
import com.alex.qasystem.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Map;

@Service
public interface UserService {

    User getUserByEmailAndPassword(String email, String password);

    UserAuthExecution login(String email, String password);

    UserRegistrationExecution register(String email, String profileName, String password);

    User getUserIdByToken(String token);

    void updateReputation(Integer userId);

    User updateProfileImg(User user, CommonsMultipartFile profileImg);

    Map<String, Object> changePassword(User user, String password, String oldPassword);


}
