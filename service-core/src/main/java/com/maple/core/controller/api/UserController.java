package com.maple.core.controller.api;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maple.base.pojo.to.UserClaims;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.RegexValidateUtils;
import com.maple.core.pojo.dto.UserInfoDTO;
import com.maple.core.pojo.entity.User;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;
import com.maple.core.service.UserInfoService;
import com.maple.core.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 客户端用户 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/core/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private UserInfoService userInfoService;

    @ApiOperation("测试")
    @GetMapping("/hello")
    public R hello() {
        return R.ok();
    }


    @ApiOperation("用户注册")
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

        //注册
        String ip = request.getRemoteAddr();
        //判断用户是否已被注册
        User user = userService.register(registerVO, ip);
        if (user != null && user.getId() != null) {
            boolean result = userInfoService.registerInfo(user.getId());
            if (result) {
                return R.ok().message("注册成功");
            } else {
                return R.error().message("注册失败, 请联系管理员!");
            }
        } else {
            return R.error().message("注册失败, 请联系管理员!");
        }
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVO loginVO, HttpServletRequest request) {  //HttpServletRequest 自动获取（前端无须传值） 请求对象，获取到当前发访问的IP地址
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();

        log.info("用户名--->>{}",username);
        log.info("密码----->>{}",password);

        Assert.notEmpty(username, ResponseEnum.LOGIN_ERROR);
        Assert.notEmpty(password, ResponseEnum.LOGIN_ERROR);

        String ip = request.getRemoteAddr();
        // 包装用户信息对象 返回
        UserInfoDTO userInfoDTO = userService.login(loginVO, ip);
        if (userInfoDTO != null) {
            return R.ok().data("user", userInfoDTO);
        } else {
            return R.setResult(ResponseEnum.LOGIN_ERROR);
        }
    }

    @ApiOperation("刷新令牌")
    @GetMapping("/refreshToken/{userId}/{userName}")
    public R refreshToken(
            @ApiParam(value = "用户ID", required = true)
            @PathVariable String userId,
            @ApiParam(value = "用户账号", required = true)
            @PathVariable String userName,HttpServletRequest request) {

        Assert.notEmpty(userId, ResponseEnum.AUTH_FAIL);
        Assert.notEmpty(userName, ResponseEnum.AUTH_FAIL);

        String token = request.getHeader("token");
        Assert.notEmpty(token, ResponseEnum.AUTH_FAIL);

        UserClaims userClaims = JwtUtils.getUserClaims(token);
        Assert.notNull(userClaims, ResponseEnum.AUTH_FAIL);

        log.info("---userId-1->{}",userId);
        log.info("---userName-1->{}",userName);

        log.info("---userId-2->{}",userClaims.getUserId());
        log.info("---userName-2->{}",userClaims.getUserName());

        Assert.equals(userId,userClaims.getUserId().toString(),ResponseEnum.AUTH_FAIL);
        Assert.equals(userName,userClaims.getUserName(),ResponseEnum.AUTH_FAIL);

        String newToken = JwtUtils.createToken(userClaims.getUserId(),userClaims.getUserName());
        if(!StringUtils.isEmpty(newToken)) {
            return R.ok().data("token",newToken);
        }
        return R.error();
    }

    @ApiOperation("锁定和解锁用户")
    @PostMapping("/lockUser/{status}")
    public R lockUser(
            @ApiParam(value = "用户状态(0:解锁,1:锁定)", required = true)
            @PathVariable String status, HttpServletRequest request) {
        Assert.notEmpty(status, ResponseEnum.PARAMETER_ERROR);

        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        log.info("----lockUser--->>>{}",userId);
//        User user = userService.getUser(userId);

        if(StringUtils.equals(status,"0") || StringUtils.equals(status,"1")) {
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            // 只更新部分字段
            updateWrapper
                    .eq("id",userId)
                    .set("status",Integer.valueOf(status));
            boolean result =  userService.update(null,updateWrapper);
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


}

