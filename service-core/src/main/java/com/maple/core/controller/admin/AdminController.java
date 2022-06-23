package com.maple.core.controller.admin;


import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.entity.Admin;
import com.maple.core.pojo.entity.User;
import com.maple.core.service.AdminService;
import com.maple.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 管理系统用户 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "管理员用户接口")
@RestController
@RequestMapping("/admin/core/user")
@Slf4j
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @ApiOperation("删除用户")
    @Delete("/deleteUserById")
    public R deleteUserById(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable String userId,
            HttpServletRequest request){

        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long adminUserId = JwtUtils.getUserId(token);
        Admin admin = adminService.getById(adminUserId);
        Assert.notNull(admin, ResponseEnum.ACCOUNT_NOT_FOUNT);

        if(admin.getStatus().intValue() == Admin.STATUS_LOCKED.intValue()) {
            return R.error().message("您的账号已被锁定,无法操作！");
        }

        User user = userService.getById(userId);
        Assert.notNull(user, ResponseEnum.ACCOUNT_NOT_FOUNT);

        return R.error();
    }

}

