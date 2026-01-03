package com.chenbitao.activiti_in_action.activiti_business.controller;

import com.chenbitao.activiti_in_action.activiti_business.domain.User;
import com.chenbitao.activiti_in_action.activiti_business.service.UserService;
import com.chenbitao.activiti_in_action.activiti_business.service.impl.UserServiceImpl;
import com.chenbitao.activiti_in_action.activiti_business.vo.ApiResponseBody;
import com.chenbitao.activiti_in_action.activiti_business.vo.UserVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ApiResponseBody<List<UserVO>> getAllUsers() {
        return ApiResponseBody.success(this.userService.listAllUsernames());
    }
}
