package com.arc.app.contact.model;

import java.util.List;

/**
 * @author 叶超
 * @since 2020/2/28 12:47
 */
public class AppContactRequest {

    /**
     * 发起人的身份标记
     */
    private String userFlag;

    //同步方案
    private Integer type = 0;

    private List<AppContact> contacts;

    @Deprecated
    private Integer state = 0;

    public AppContactRequest(String userFlag, List<AppContact> contacts, int type) {
        this.userFlag = userFlag;
        this.contacts = contacts;
        this.type = type;

    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<AppContact> getContacts() {
        return contacts;
    }

    public void setContacts(List<AppContact> contacts) {
        this.contacts = contacts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
