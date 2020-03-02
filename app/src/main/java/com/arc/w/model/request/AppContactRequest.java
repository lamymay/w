package com.arc.w.model.request;

import com.arc.w.model.AppContact;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 叶超
 * @since 2020/2/28 12:47
 */
@Setter
@Getter
@NoArgsConstructor
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
}
