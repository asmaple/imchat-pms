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
 * 客户端用户
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="客户端用户")
public class User implements Serializable {
    // 用户状态
    public static  final Integer STATUS_NORMAL = 0; // 正常
    public static  final Integer STATUS_LOCKED = 1; // 锁定

    public static final String SIGN_VALUE = "maple";
    public static final String NICKNAME_VALUE = "maple会员";

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "性别(0: 未知，1: 男， 2:女)")
    private Integer gender;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "用户头像")
    private String avatarUrl;

    @ApiModelProperty(value = "登录令牌")
    private String token;

    @ApiModelProperty(value = "开放id")
    private String openid;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "最后一次登录IP")
    private String ip;

    @ApiModelProperty(value = "用户签名")
    private String sign;

    @ApiModelProperty(value = "用户状态(0: 正常)")
    private Integer status;

    @ApiModelProperty(value = "用户是否删除(0: 删除，1: 正常)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
