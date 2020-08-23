package com.arc.model;

import java.util.List;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
public class AppContact {

    private static final long serialVersionUID = 1L;

    //后台统中的id是主键
    private Integer id;

    //后台统唯一的用户id
    private Long userId;

    //是每个通讯录中的id，不唯一，每个设备上的通讯录id各自独立
    private Integer contactId;

    public String displayName;
    public String nickname;

    public String cellphone;

    //电话号码集合
    private List<String> phones;
    private List<String> emails;

    private String telephone;
    private String email;

    private String im;
    private String photo;
    private String organization;
    private String postalAddress;
    private String groupMembership;


    //保留字段
    private String name;
    public String hasPhoneNumber;
    public Integer state;
//    private LocalDateTime createTime;
//    private LocalDateTime updateTime;

    public String note;

    //=====================================
    public AppContact(   ) {
    }
    public AppContact(String displayName, String phoneNumber) {
        this.displayName = displayName;
        this.cellphone = phoneNumber;
    }

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getGroupMembership() {
        return groupMembership;
    }

    public void setGroupMembership(String groupMembership) {
        this.groupMembership = groupMembership;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(String hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
