package com.maple.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 消息
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Message对象", description="消息")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息雪花uuid")
    @TableId(value = "uuid", type = IdType.AUTO)
    private String uuid;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "联系人id")
    private Long bookId;

    @ApiModelProperty(value = "消息类型(0: 文本消息)")
    private Integer type;

    @ApiModelProperty(value = "是否逻辑删除(0: 删除，1:正常)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "消息状态")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "内容")
    private String content;


}
