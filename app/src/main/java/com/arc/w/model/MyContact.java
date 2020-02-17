package com.arc.w.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
@Getter
@Setter
@ToString
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

}
