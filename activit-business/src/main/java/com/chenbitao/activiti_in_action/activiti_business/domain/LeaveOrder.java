package com.chenbitao.activiti_in_action.activiti_business.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chenbitao.activiti_in_action.activiti_business.domain.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("`t_biz_leave`")
public class LeaveOrder extends BaseEntity<Long> {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String userName;
    private Integer duration;
    private String processInstanceId; // 核心：关联 Activiti 的流程实例 ID
}