package com.maple.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 广告
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysAd对象", description="广告")
@JsonIgnoreProperties({"deleted","updateTime"}) // 指定 response 中忽略的字段
public class SysAd implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "广告id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Ad类型(0: 图文)")
    private Integer type;

    @ApiModelProperty(value = "ad标题")
    private String title;

    @ApiModelProperty(value = "ad 图片链接")
    private String adUrl;

    @ApiModelProperty(value = "逻辑删除(0: 删除，1: 正常)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
