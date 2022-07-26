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
 * 管理系统用户
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Admin对象", description="管理系统用户")
@JsonIgnoreProperties({"password","deleted","updateTime","token","sign"}) // 指定 response 中忽略的字段
public class Admin implements Serializable {

    // 用户状态
    public static  final Integer STATUS_NORMAL = 0; // 正常
    public static  final Integer STATUS_LOCKED = 1; // 锁定

    public static final String SIGN_VALUE = "maple";

    public static final Integer ROLE_USER = 0;
    public static final Integer ROLE_ADMIN = 1;
    public static final Integer ROLE_ROOT = 99;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "账号状态")
    private Integer status;

    @ApiModelProperty(value = "是否逻辑删除(0: 删除，1:正常)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "角色(0: user , 1: admin,  99: root)")
    private Integer roleCode;

    @ApiModelProperty(value = "登录ip")
    private String ip;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @ApiModelProperty(value = "登录令牌")
    private String token;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


}
