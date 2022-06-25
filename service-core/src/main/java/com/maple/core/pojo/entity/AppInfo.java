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
 * 应用程序文件
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="AppInfo对象", description="应用程序文件")
@JsonIgnoreProperties({"deleted","updateTime"}) // 指定 response 中忽略的字段
public class AppInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "应用程序ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "文件信息ID")
    private Long fileInfoId;

    @ApiModelProperty(value = "APP类型（0：Android  1：IOS）")
    private Integer type;

    @ApiModelProperty(value = "是否更新（0：不更新  1：更新）")
    private Boolean hasUpdate;

    @ApiModelProperty(value = "是否可忽略该版本（是否强制更新）（0： 可忽略，1：不可取消）")
    @TableField("is_ignorable")
    private Boolean ignorable;

    @ApiModelProperty(value = "版本号")
    private Integer versionCode;

    @ApiModelProperty(value = "版本名")
    private String versionName;

    @ApiModelProperty(value = "更新内容")
    private String updateContent;

    @ApiModelProperty(value = "Android（下载地址） IOS（AppStore连接）")
    private String downloadUrl;

    @ApiModelProperty(value = "app id")
    private String appId;

    @ApiModelProperty(value = "逻辑删除")
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
