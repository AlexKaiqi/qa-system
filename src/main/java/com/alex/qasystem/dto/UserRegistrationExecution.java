package com.alex.qasystem.dto;

import com.alex.qasystem.entity.User;
import com.alex.qasystem.enums.UserRegistrationStateEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;

public class UserRegistrationExecution {
    private Integer state;
    private String stateInfo;
    private User user;

    public UserRegistrationExecution(UserRegistrationStateEnum userRegistrationStateEnum) {
        this(userRegistrationStateEnum, null);
    }

    public UserRegistrationExecution(UserRegistrationStateEnum userRegistrationStateEnum, User user) {
        this.state = userRegistrationStateEnum.getState();
        this.stateInfo = userRegistrationStateEnum.getStateInfo();
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "cannot parse object as json" + this.hashCode();
    }
}
