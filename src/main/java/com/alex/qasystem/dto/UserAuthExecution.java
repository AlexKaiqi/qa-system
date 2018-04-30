package com.alex.qasystem.dto;

import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserAuthStateEnum;

public class UserAuthExecution {

    private Integer state;
    private String stateInfo;
    private String token;
    private User user;

    public UserAuthExecution(UserAuthStateEnum userAuthStateEnum) {
        this.state = userAuthStateEnum.getState();
        this.stateInfo = userAuthStateEnum.getStateInfo();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
