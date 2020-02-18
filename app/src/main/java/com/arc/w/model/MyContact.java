package com.arc.w.model;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
public class MyContact {

    public String contactId;
    public String name;
    public String phone;

    public MyContact(String name, String phone) {
        this.name = name;
        this.name = phone;
    }

    public MyContact() {
    }

//    public String note;

    //    public String id;
    //    public String nickname;
    //    public List<String> phones;
    //    public String email;



    public MyContact(String contactId, String name, String phone) {
        this.contactId = contactId;
        this.name = name;
        this.phone = phone;
    }

    // getter setter
    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
