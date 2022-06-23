package com.maple.core.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="用户信息对象")
public class UserInfoDTO {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别(0: 未知，1: 男， 2:女)")
    private Integer gender;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "登录令牌")
    private String token;

    @ApiModelProperty(value = "开放id")
    private String openid;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "最后一次登录IP")
    private String lastIp;

}