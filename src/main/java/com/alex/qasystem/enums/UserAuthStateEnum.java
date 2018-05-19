package com.alex.qasystem.enums;

/**
 * 用户身份验证, 登录, 注册返回状态
 *
 * @author Alex
 */
public enum UserAuthStateEnum {

    /**
     * SIGN_IN_FAIL 用户存在但与密码不匹配
     * SUCCESS 登录成功
     * NULL_AUTH_INFO 注册信息为空
     */
    SUCCESS(0, "身份验证成功"),
    ALREADY_LOGGED_IN(1, "用户已经处于登录状态了"),
    WRONG_PASSWORD(-1, "帐号密码不匹配"),
    USER_NOT_EXISTS(-1003, "该邮箱尚未注册"),
    TOKEN_NOT_EXISTS_OR_EXPIRED(-1004, "token不存在或已经过期"),
    NULL_AUTH_INFO(-1005, "缺少身份验证信息"),
    INVALID_LOGIN_PARAMETERS(-1006, "登录信息不合法");

    private int state;

    private String stateInfo;

    UserAuthStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static UserAuthStateEnum stateOf(int index) {
        for (UserAuthStateEnum state : values()) {
            if (state.getState() == index) {
                return state;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

}
