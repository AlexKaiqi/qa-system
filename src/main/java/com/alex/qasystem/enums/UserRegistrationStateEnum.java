package com.alex.qasystem.enums;

public enum  UserRegistrationStateEnum {

    /**
     *
     */
    SUCCESS(0, "注册成功"),
    INVALID_REGISTRATION_INFO(-1001, "注册信息不合法"),
    USER_ALREADY_EXISTS(-1002, "邮箱已经被注册");

    private int state;
    private String stateInfo;

    UserRegistrationStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static UserRegistrationStateEnum stateOf(int index) {
        for (UserRegistrationStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }
}
