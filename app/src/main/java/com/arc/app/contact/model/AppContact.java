package com.arc.app.contact.model;

import java.util.List;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
public class AppContact {

    private static final long serialVersionUID = 1L;

    private Integer id;//后台统中的id是主键
    private Long userId;//后台统唯一的用户id
    private Integer contactId;//是每个通讯录中的id，不唯一，每个设备上的通讯录id各自独立


    public String lastname;//英文中的人名姓氏
    public String givenName;//名字 = given name  firstname
    private String name;//名称= lastname + firstname
    @Deprecated
    public String nickname;//昵称 等效displayName
    public String displayName;
    private List<String> phones; //电话号码集合
    private List<String> emails;
    private List<String> ims;

    public String jsonString;//预留jsonString
    public Integer state;
    public String note;

    private String photo;
    private String organization;
    private String postalAddress;
    private String groupMembership;


    @Deprecated
    public String cellphone;

    @Deprecated
    private String telephone;

    @Deprecated
    private String email;

    @Deprecated
    private String im;



    @Deprecated
    public String hasPhoneNumber;

//    private LocalDateTime createTime;
//    private LocalDateTime updateTime;



    public AppContact() {
    }

    public AppContact(String displayName, String phoneNumber) {
        this.displayName = displayName;
        this.cellphone = phoneNumber;
    }
    //=====================================


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

    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public List<String> getIms() {
        return ims;
    }

    public void setIms(List<String> ims) {
        this.ims = ims;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getHasPhoneNumber() {
        return hasPhoneNumber;
    }

    public void setHasPhoneNumber(String hasPhoneNumber) {
        this.hasPhoneNumber = hasPhoneNumber;
    }

    @Override
    public String toString() {
        return "AppContact{" +
                "id=" + id +
                ", userId=" + userId +
                ", contactId=" + contactId +
                ", lastname='" + lastname + '\'' +
                ", givenName='" + givenName + '\'' +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", displayName='" + displayName + '\'' +
                ", phones=" + phones +
                ", emails=" + emails +
                ", ims=" + ims +
                ", jsonString='" + jsonString + '\'' +
                ", state=" + state +
                ", note='" + note + '\'' +
                ", photo='" + photo + '\'' +
                ", organization='" + organization + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", groupMembership='" + groupMembership + '\'' +
                ", cellphone='" + cellphone + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", im='" + im + '\'' +
                ", hasPhoneNumber='" + hasPhoneNumber + '\'' +
                '}';
    }
}
