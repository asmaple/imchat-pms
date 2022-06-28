package com.maple.core.controller.api;


import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.dto.AppDTO;
import com.maple.core.pojo.entity.AppInfo;
import com.maple.core.pojo.entity.FileInfo;
import com.maple.core.service.AppInfoService;
import com.maple.core.service.FileInfoService;
import com.maple.core.utils.MinIoUtil;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * 应用程序文件 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "应用程序接口")
@RequestMapping("/api/core/appInfo")
@RestController
@Slf4j
public class AppInfoController {

    @Resource
    private AppInfoService appInfoService;
    @Resource
    private FileInfoService fileInfoService;


    @ApiOperation("根据包名获取APP信息")
    @GetMapping("/checkAppVersion")
    public R checkAppVersion(
            @ApiParam(value = "应用包名",required = true)
            @RequestParam("appId") String appId,
            @ApiParam(value = "APP类型（0：Android  1：IOS）",required = true)
            @RequestParam("appType") String appType){

        Assert.notEmpty(appId, ResponseEnum.PARAMETER_ERROR);
        AppInfo appInfo = appInfoService.getAppInfoByPackageName(appId,appType);
        if(appInfo != null){
            return R.ok().data("appInfo",appInfo);
        }
        return R.error();
    }

    /**
     * 文件APP
     * @param file
     * @return
     */
    @ApiOperation("上传APP")
    @PostMapping("/uploadApp")
    @ResponseBody
    public R uploadApp(
            @ApiParam(value= "APP文件", required = true)
            @RequestParam("file") MultipartFile file,
            @ApiParam(value = "APP类型（0：Android 1：IOS）", required = true)
            @RequestParam("type") Integer type,
            @ApiParam(value = "是否更新（0：不更新  1：更新）", required = true)
            @RequestParam("hasUpdate") Boolean hasUpdate,
            @ApiParam(value = "是否可忽略该版本（是否强制更新）（0： 可忽略，1：不可取消）", required = true)
            @RequestParam("isIgnorable") Boolean isIgnorable,
            @ApiParam(value = "版本号", required = true)
            @RequestParam("versionCode") Integer versionCode,
            @ApiParam(value = "版本名", required = true)
            @RequestParam("versionName") String versionName,
            @ApiParam(value = "更新内容", required = true)
            @RequestParam("updateContent") String updateContent,
            @ApiParam(value = "应用包名", required = true)
            @RequestParam("appId") String appId) {

        AppDTO appDTO = fileInfoService.uploadApp(file, MinIoUtil.BUCKET_NAME,"app");
        if(appDTO != null){
            appDTO.setType(type);
            appDTO.setHasUpdate(hasUpdate);
            appDTO.setIsIgnorable(isIgnorable);
            appDTO.setVersionCode(versionCode);
            appDTO.setVersionName(versionName);
            appDTO.setUpdateContent(updateContent);
            appDTO.setAppId(appId);
            boolean result = appInfoService.saveAppInfo(appDTO);
            if(result){
                return R.ok().message("APP上传成功");
            }
        }
        return R.error().message("APP上传失败");
    }
}

