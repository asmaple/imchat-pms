package com.maple.core.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.RegexValidateUtils;
import com.maple.core.pojo.dto.AdminDTO;
import com.maple.core.pojo.dto.UserInfoDTO;
import com.maple.core.pojo.entity.Admin;
import com.maple.core.pojo.entity.User;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;
import com.maple.core.service.AdminService;
import com.maple.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "管理员接口")
@RestController
@RequestMapping("/admin/core/user")
@Slf4j
public class AdminController {

    @Resource
    private AdminService adminService;

    @Resource
    private UserService userService;

    @ApiOperation("管理员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVO registerVO, HttpServletRequest request) {

        String username = registerVO.getUsername();
        String password = registerVO.getPassword();
        String phone = registerVO.getPhone();
        String email = registerVO.getEmail();
        String code = registerVO.getCode();

        Assert.notEmpty(username, ResponseEnum.PARAMETER_ERROR);
        Assert.notEmpty(password, ResponseEnum.PARAMETER_ERROR);
        Assert.notEmpty(code, ResponseEnum.CODE_ERROR);
        Assert.isTrue(RegexValidateUtils.checkCellphone(phone), ResponseEnum.PHONE_ERROR);
        Assert.isTrue(RegexValidateUtils.checkPassword(password), ResponseEnum.PASSWORD_ERROR);
        Assert.isTrue(RegexValidateUtils.checkEmail(email), ResponseEnum.EMAIL_ERROR);

        //校验验证码是否正确
//        String codeGen = (String)redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
//        String codeGen = redisTemplate.opsForValue().get("srb:sms:code:" + mobile);
//        Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

        //判断用户是否已被注册
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper
                .eq("username", registerVO.getUsername())
                .or()
                .eq("phone",registerVO.getPhone());

        Admin admin = adminService.getOne(adminQueryWrapper);
        Assert.isNull(admin,ResponseEnum.ACCOUNT_EXIST_ERROR);

        //注册
        String ip = request.getRemoteAddr();
        boolean result = adminService.register(registerVO,ip);
        if(result) {
            return R.ok().message("注册成功");
        }
        return R.error().message("注册失败, 请联系管理员!");
    }

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();

        log.info("用户名--->>{}",username);
        log.info("密码----->>{}",password);

        Assert.notEmpty(username, ResponseEnum.LOGIN_ERROR);
        Assert.notEmpty(password, ResponseEnum.LOGIN_ERROR);

        String ip = request.getRemoteAddr();

        // 包装用户信息对象 返回
        AdminDTO adminDTO = adminService.login(loginVO,ip);
        if (adminDTO != null) {
            return R.ok().data("user", adminDTO);
        } else {
            return R.setResult(ResponseEnum.LOGIN_ERROR);
        }
    }


    @ApiOperation("锁定和解锁用户")
    @PostMapping("/lockUser/{status}")
    public R lockUser(
            @ApiParam(value = "用户ID", required = true)
            @PathVariable String userId,
            @ApiParam(value = "用户状态(0:解锁,1:锁定)", required = true)
            @PathVariable String status, HttpServletRequest request) {

        Assert.notEmpty(userId, ResponseEnum.PARAMETER_ERROR);
        Assert.notEmpty(status, ResponseEnum.PARAMETER_ERROR);

        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long mUserId = JwtUtils.getUserId(token);
        log.info("----lockUser--->>>{}",mUserId);
        Admin admin = adminService.getById(mUserId);
        Assert.notNull(admin,ResponseEnum.ACCOUNT_NOT_FOUNT);

        // 用户被锁定
        Assert.equals(admin.getStatus(), User.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);


        if(StringUtils.equals(status,"0") || StringUtils.equals(status,"1")) {
            UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
            // 只更新部分字段
            updateWrapper
                    .eq("id",userId)
                    .set("status",Integer.valueOf(status));
            boolean result =  adminService.update(null,updateWrapper);
            if(result) {
                return R.ok().message(StringUtils.equals(status,"1")? "锁定成功！" : "解锁成功！");
            }
        }
        return R.error();
    }


    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }


    @ApiOperation("删除用户")
    @PostMapping("/deleteUserById/{userId}")
    public R deleteUserById(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable String userId,
            HttpServletRequest request){

        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long adminUserId = JwtUtils.getUserId(token);
        Admin admin = adminService.getById(adminUserId);
        Assert.notNull(admin, ResponseEnum.ACCOUNT_NOT_FOUNT);

        // 账号被锁定
        Assert.equals(admin.getStatus(), Admin.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);
        // 无权限
        Assert.isTrue((admin.getRoleCode().intValue() == Admin.ROLE_ADMIN.intValue() || admin.getRoleCode().intValue() == Admin.ROLE_ROOT.intValue()), ResponseEnum.ROLE_NOT_PERMISSION);

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        // 只更新部分字段
        updateWrapper
                .eq("id",userId)
                .set("is_deleted",true);

        boolean result =  userService.update(null,updateWrapper);
        if(result) {
            return R.ok().message("删除成功！");
        }
        return R.error().message("删除失败！");
    }

}

