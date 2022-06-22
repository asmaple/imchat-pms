package com.maple.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户详情
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UserInfo对象", description="用户详情")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "身份证ID")
    private String idcard;

    @ApiModelProperty(value = "身份证人物照")
    private String frontUrl;

    @ApiModelProperty(value = "身份证国徽照")
    private String backUrl;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别(0: 未知，1: 男， 2:女)")
    private Integer gender;

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

    @ApiModelProperty(value = "是否逻辑删除(0: 删除，1:正常)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
