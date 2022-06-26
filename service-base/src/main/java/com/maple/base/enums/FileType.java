package com.maple.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 文件上传类型
 */
@Getter
@AllArgsConstructor
public enum FileType {
    /**
     * 头像
     */
    AVATAR("avatar"),

    /**
     * 身份证人物照
     */
    ID_FRONT("id-front"),

    /**
     * 身份证国徽照
     */
    ID_BACK("id-back");

    /**
     * 类型
     */
    private final String type;
}
