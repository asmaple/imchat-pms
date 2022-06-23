package com.maple.core.controller.api;


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
import lombok.extern.slf4j.Slf4j;
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



    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public R logout() {
        return R.ok();
    }


}

