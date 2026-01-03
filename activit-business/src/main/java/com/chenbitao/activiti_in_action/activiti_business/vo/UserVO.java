package com.chenbitao.activiti_in_action.activiti_business.vo;

import com.chenbitao.activiti_in_action.activiti_business.annotation.Sensitive;
import com.chenbitao.activiti_in_action.activiti_business.enums.MaskType;
import lombok.Data;
import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private Long id;

    @Sensitive(type = MaskType.USERNAME)
    private String username;

    @Sensitive(type = MaskType.NAME)
    private String name;

    private Integer age;

    @Sensitive(type = MaskType.EMAIL)
    private String email;
}