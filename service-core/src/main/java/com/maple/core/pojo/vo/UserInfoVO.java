package com.maple.core.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="用户信息对象")
public class UserInfoVO {

    @ApiModelProperty(value = "身份证ID")
    private String idcard;

    @ApiModelProperty(value = "身份证人物照")
    private String frontUrl;

    @ApiModelProperty(value = "身份证国徽照")
    private String backUrl;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0: 未知，1: 男， 2:女)")
    private String gender;

    @ApiModelProperty(value = "族")
    private String ethnicity;

    @ApiModelProperty(value = "出生日期")
    private String birthday;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "签发单位(公安局)")
    private String authority;

    @ApiModelProperty(value = "有效日期")
    private String effectiveDate;
}