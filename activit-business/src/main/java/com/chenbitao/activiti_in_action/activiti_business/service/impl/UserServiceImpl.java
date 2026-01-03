package com.chenbitao.activiti_in_action.activiti_business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenbitao.activiti_in_action.activiti_business.domain.User;
import com.chenbitao.activiti_in_action.activiti_business.mapper.UserMapper;
import com.chenbitao.activiti_in_action.activiti_business.service.UserService;
import com.chenbitao.activiti_in_action.activiti_business.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public List<UserVO> listAllUsernames() {
//        return this.list();
        List<User> userList = this.query()
                .select("id", "username", "name", "age", "email")
                .list();
        
        // 2. 将 User 转换为 UserVO
        return userList.stream().map(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
