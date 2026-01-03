package com.chenbitao.activiti_in_action.activiti_business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenbitao.activiti_in_action.activiti_business.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}