package com.chenbitao.activiti_in_action.activiti_business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenbitao.activiti_in_action.activiti_business.domain.User;
import com.chenbitao.activiti_in_action.activiti_business.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 查询所有用户名
     * @return 用户名列表
     */
    List<UserVO> listAllUsernames();
}
