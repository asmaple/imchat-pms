package com.maple.core.controller.api;


import com.maple.common.result.R;
import com.maple.core.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户详情 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "用户信息接口")
@RestController
@RequestMapping("/api/core/userInfo")
@Slf4j
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public R updateUserInfo() {

        return R.ok();
    }

}

