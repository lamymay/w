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
public class AppContact {

    private static final long serialVersionUID = 1L;

    //本系统中的id是主键
    private Integer id;

    //是每个通讯录中的id，不唯一，每个设备上的通讯录id各自独立
    private Integer contactId;

    //    public String phone;
    public String displayName;
    public String cellphone;

    public String nickname;


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


}
