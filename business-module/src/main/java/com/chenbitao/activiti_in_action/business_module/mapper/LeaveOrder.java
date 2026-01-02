package com.chenbitao.activiti_in_action.business_module.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("biz_leave")
public class LeaveOrder {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String userName;
    private Integer duration;
    private String processInstanceId; // 核心：关联 Activiti 的流程实例 ID
}