package com.alex.qasystem.enums;

/**
 * �û���֤����״̬
 *
 * @author Alex
 */
public enum UserAuthStateEnum {

    /**
     * LOGIN_FAIL �û����ڵ������벻ƥ��
     * SUCCESS ��¼�ɹ�
     * NULL_AUTH_INFO ע����ϢΪ��
     */
    LOGIN_FAIL(-1, "������ʺ���������"),
    SUCCESS(0, "�����ɹ�"),
    NULL_AUTH_INFO(-1006,"ע����ϢΪ��");

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
