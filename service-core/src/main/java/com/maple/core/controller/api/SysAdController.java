package com.maple.core.controller.api;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.entity.*;
import com.maple.core.pojo.vo.SysAdVO;
import com.maple.core.pojo.vo.UserInfoVO;
import com.maple.core.service.AdminService;
import com.maple.core.service.AppInfoService;
import com.maple.core.service.SysAdService;
import com.maple.core.utils.PageUtils;
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
 * 广告 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "广告接口")
@RestController
@RequestMapping("/api/core/sysAd")
@Slf4j
public class SysAdController {


    @Resource
    private AdminService adminService;

    @Resource
    private SysAdService sysAdService;


    @ApiOperation("创建AD")
    @PostMapping("/addAdInfo")
    public R addAdInfo(@RequestBody SysAdVO sysAdVO, HttpServletRequest request) {
        Assert.notNull(sysAdVO, ResponseEnum.PARAMETER_ERROR);
        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        log.info("----userId--->>>{}",userId);
        Admin admin = adminService.getById(userId);
        Assert.notNull(admin, ResponseEnum.ACCOUNT_NOT_FOUNT);
        // 用户被锁定
        Assert.equals(admin.getStatus(), User.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);
        // 无权限
        Assert.isTrue((admin.getRoleCode().intValue() == Admin.ROLE_ROOT.intValue()), ResponseEnum.ROLE_NOT_PERMISSION);
        boolean result = sysAdService.saveSysAd(sysAdVO);
        if(result) {
            return R.ok().message("添加成功!");
        }
        return R.error().message("添加失败!");
    }



    @ApiOperation("查询Ad列表")
    @GetMapping("/list")
    public R getAdListByType(
            @ApiParam(value = "当前页码", required = true)
            @RequestParam("page") Long page,

            @ApiParam(value = "每页记录数", required = true)
            @RequestParam("limit") Long limit,

            @ApiParam(value = "文件类型", required = true)
            @RequestParam("type") Integer type,

            @ApiParam(value = "查询关键字", required = false)
            @RequestParam("keyword") String keyword){

        Page<SysAd> pageParam = new Page<>(page, limit);
        IPage<SysAd> pageModel =  sysAdService.listPage(pageParam, keyword, type);
        return PageUtils.page(page,pageModel);
    }

}

