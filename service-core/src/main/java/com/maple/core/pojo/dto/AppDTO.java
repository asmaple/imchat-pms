package com.maple.core.pojo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Tolerate;

@Data
@Builder
@ToString
public class AppDTO {

    private Long fileInfoId;

    private Integer type;

    private Boolean hasUpdate;

    private Boolean isIgnorable;

    private Integer versionCode;

    private String versionName;

    private String updateContent;

    private String downloadUrl;

    private String appId;

    @Tolerate
    public AppDTO() {

    }
}
