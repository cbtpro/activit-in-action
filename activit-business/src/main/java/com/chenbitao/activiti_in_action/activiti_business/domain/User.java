package com.chenbitao.activiti_in_action.activiti_business.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.activiti_in_action.activiti_business.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`t_user`")
public class User extends BaseEntity<Long> {
    private String username;
    private String password;
    private String name;
    private Integer age;
    private String email;
}