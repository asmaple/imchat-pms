package com.maple.core.controller.api;


import com.maple.common.result.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("测试")
    @GetMapping("/hello")
    public R hello() {
        return R.ok();
    }

}

