package com.alex.qasystem.enums;

/**
 * �û������֤, ��¼, ע�᷵��״̬
 *
 * @author Alex
 */
public enum UserAuthStateEnum {

    /**
     * SIGN_IN_FAIL �û����ڵ������벻ƥ��
     * SUCCESS ��¼�ɹ�
     * NULL_AUTH_INFO ע����ϢΪ��
     */
    SUCCESS(0, "�����֤�ɹ�"),
    ALREADY_LOGGED_IN(1, "�û��Ѿ����ڵ�¼״̬��"),
    WRONG_PASSWORD(-1, "�ʺ����벻ƥ��"),
    USER_NOT_EXISTS(-1003, "��������δע��"),
    TOKEN_NOT_EXISTS_OR_EXPIRED(-1004, "token�����ڻ��Ѿ�����"),
    NULL_AUTH_INFO(-1005, "ȱ�������֤��Ϣ"),
    INVALID_LOGIN_PARAMETERS(-1006, "��¼��Ϣ���Ϸ�");

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
