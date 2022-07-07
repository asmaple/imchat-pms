package com.maple.core.pojo.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Ad对象")
public class SysAdVO {

    @ApiModelProperty(value = "Ad类型(0: 图文)")
    private Integer type;

    @ApiModelProperty(value = "ad标题")
    private String title;

    @ApiModelProperty(value = "ad 图片链接")
    private String adUrl;
}