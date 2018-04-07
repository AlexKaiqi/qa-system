package com.alex.qasystem.enums;

/**
 * 用户验证返回状态
 *
 * @author Alex
 */
public enum UserAuthStateEnum {

    /**
     * LOGIN_FAIL 用户存在但与密码不匹配
     * SUCCESS 登录成功
     * NULL_AUTH_INFO 注册信息为空
     */
    LOGIN_FAIL(-1, "密码或帐号输入有误"),
    SUCCESS(0, "操作成功"),
    NULL_AUTH_INFO(-1006,"注册信息为空");

    private int state;

    private String stateInfo;

    UserAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static UserAuthStateEnum stateOf(int index) {
        for (UserAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

}
