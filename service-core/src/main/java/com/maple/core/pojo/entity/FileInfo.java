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
 * 文件
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FileInfo对象", description="文件")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "桶名")
    private String bucketName;

    @ApiModelProperty(value = "文件名")
    private String objectName;

    @ApiModelProperty(value = "模块名")
    private String moduleName;

    @ApiModelProperty(value = "重置名")
    private String fileRename;

    @ApiModelProperty(value = "文件完整连接地址")
    private String fileUrl;

    @ApiModelProperty(value = "文件原始名")
    private String originalFilename;

    @ApiModelProperty(value = "文件类型")
    private String fileType;

    @ApiModelProperty(value = "文件长度")
    private Long fileSize;

    @ApiModelProperty(value = "文件秘钥")
    private String encryptKey;

    @ApiModelProperty(value = "是否逻辑删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
