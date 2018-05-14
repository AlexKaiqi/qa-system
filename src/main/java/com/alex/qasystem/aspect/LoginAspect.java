package com.alex.qasystem.aspect;

import com.alex.qasystem.dto.UserAuthExecution;
import com.alex.qasystem.entity.User;
import com.alex.qasystem.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoginAspect {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @AfterReturning(pointcut = "execution(* com.alex.qasystem.service.UserService.login(..)),",
            returning = "userAuthExecution")
    public User updateReputation(JoinPoint joinPoint, UserAuthExecution userAuthExecution) {
        if (userAuthExecution.getUser() == null) {
            return null;
        }
        userService.updateReputation(userAuthExecution.getUser().getId());
        return userAuthExecution.getUser();
    }

}
