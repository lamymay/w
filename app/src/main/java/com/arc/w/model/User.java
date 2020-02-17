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
public class User {

    public String name;
    public String phoneNumber;
    //    public String id;
    //    public String note;
    //    public String nickname;
    //    public List<String> phones;
    //    public String email;

}
