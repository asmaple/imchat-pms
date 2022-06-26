package com.maple.core.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.result.R;
import com.maple.core.pojo.entity.User;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.service.UserLoginRecordService;
import com.maple.core.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 用户登录记录 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "用户登录记录接口")
@RestController
@RequestMapping("/api/core/userLoginRecord")
@Slf4j
public class UserLoginRecordController {

    @Resource
    private UserLoginRecordService userLoginRecordService;

    @ApiOperation("查询用户登录记录列表")
    @GetMapping("/list")
    public R getUserList(
            @ApiParam(value = "当前页码", required = true)
            @RequestParam("page") Long page,

            @ApiParam(value = "每页记录数", required = true)
            @RequestParam("limit") Long limit,

            @ApiParam(value = "查询关键字", required = false)
            @RequestParam("keyword") String keyword){

        Page<UserLoginRecord> pageParam = new Page<>(page, limit);
        IPage<UserLoginRecord> pageModel =  userLoginRecordService.listPage(pageParam, keyword);
        return PageUtils.page(pageModel);
    }
}

