package com.maple.core.pojo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Data
@Builder
@ToString
public class FileDTO {
    /**
     * 桶名称
     */
    private String bucketName;
    /**
     * 对象名称
     */
    private String objectName;

    /**
     * file module name
     */
    private String moduleName;

    /**
     * 重命名的文件名称
     */
    private String fileRename;
    /**
     * 外链
     */
    private String fileUrl;

    /**
     * 文件原始名称
     */
    private String originalFilename;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     *
     */
    private Long fileSize;

    /**
     * 加密密钥
     */
    private String encryptKey;

    @Tolerate
    public FileDTO() {

    }
}
