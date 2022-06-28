package com.maple.core.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.entity.User;
import com.maple.core.pojo.entity.UserInfo;
import com.maple.core.pojo.vo.RegisterVO;
import com.maple.core.pojo.vo.UserInfoVO;
import com.maple.core.service.UserInfoService;
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

    @ApiOperation("查询用户信息")
    @GetMapping("/getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        log.info("----uploadImage--->>>{}",userId);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        return R.ok().data("userInfo",userInfo);
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public R updateUserInfo(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request) {

        Assert.notNull(userInfoVO, ResponseEnum.PARAMETER_ERROR);

        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        log.info("----uploadImage--->>>{}",userId);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper
                .eq("user_id",userId);
        if(!StringUtils.isBlank(userInfoVO.getIdcard())) {
            updateWrapper.set("idcard",userInfoVO.getIdcard());
        }
        if(!StringUtils.isBlank(userInfoVO.getFrontUrl())) {
            updateWrapper.set("front_url",userInfoVO.getFrontUrl());
        }
        if(!StringUtils.isBlank(userInfoVO.getBackUrl())) {
            updateWrapper.set("back_url",userInfoVO.getBackUrl());
        }
        if(!StringUtils.isBlank(userInfoVO.getName())) {
            updateWrapper.set("name",userInfoVO.getName());
        }
        if(StringUtils.equals("0",userInfoVO.getGender()) || StringUtils.equals("1",userInfoVO.getGender()) || StringUtils.equals("2",userInfoVO.getGender())) {
            updateWrapper.set("gender",userInfoVO.getGender());
        }
        if(!StringUtils.isBlank(userInfoVO.getEthnicity())) {
            updateWrapper.set("ethnicity",userInfoVO.getEthnicity());
        }
        if(!StringUtils.isBlank(userInfoVO.getBirthday())) {
            updateWrapper.set("birthday",userInfoVO.getBirthday());
        }
        if(!StringUtils.isBlank(userInfoVO.getAddress())) {
            updateWrapper.set("address",userInfoVO.getAddress());
        }
        if(!StringUtils.isBlank(userInfoVO.getAuthority())) {
            updateWrapper.set("authority",userInfoVO.getAuthority());
        }
        if(!StringUtils.isBlank(userInfoVO.getEffectiveDate())) {
            updateWrapper.set("effectiveDate",userInfoVO.getEffectiveDate());
        }
        boolean result = userInfoService.update(null,updateWrapper);
        if(result) {
            return R.ok().message("更新成功！");
        }
        return R.error().message("更新失败!");
    }

}

