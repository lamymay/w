package com.arc.w.model;

import lombok.*;

/**
 * 用户实体
 * 注解是使用插件 Lombok， bean可以省略手动写getter setter
 *
 * @author may
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AppContact implements Comparable {

    public Integer id;
    public String displayName;
    public String phone;

    public String hasPhoneNumber;

    public Integer state;

    //    public String id;
    //    public String note;
    //    public String nickname;
    //    public List<String> phones;
    //    public String email;

    public AppContact(String displayName, String phoneNumber) {
        this.displayName = displayName;
        this.phone = phoneNumber;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }


    //    public String note;
    //    public String nickname;
    //    public List<String> phones;
    //    public String email;

    //    public MyContact() {
    //    }


}
