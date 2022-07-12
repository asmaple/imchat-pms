package com.maple.core.controller.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.dto.AppDTO;
import com.maple.core.pojo.entity.AppInfo;
import com.maple.core.pojo.entity.FileInfo;
import com.maple.core.pojo.entity.User;
import com.maple.core.service.AppInfoService;
import com.maple.core.service.FileInfoService;
import com.maple.core.utils.MinIoUtil;
import com.maple.core.utils.PageUtils;
import io.netty.util.internal.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

    @ApiOperation("查询应用列表")
    @GetMapping("/appList")
    public R getAppList(
            @ApiParam(value = "当前页码", required = true)
            @RequestParam("page") Long page,

            @ApiParam(value = "每页记录数", required = true)
            @RequestParam("limit") Long limit,

            @ApiParam(value = "查询关键字", required = false)
            @RequestParam("keyword") String keyword){

        Page<AppInfo> pageParam = new Page<>(page, limit);
        IPage<AppInfo> pageModel =  appInfoService.listPage(pageParam, keyword);
        return PageUtils.page(pageModel);
    }


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

    @ApiOperation("根据包名获取Android 差分包信息")
    @GetMapping("/checkAppBsDiff")
    public R checkAndroidBsDiff(
            @ApiParam(value = "应用包名",required = true)
            @RequestParam("appId") String appId,

            @ApiParam(value = "当前版本号）",required = true)
            @RequestParam("versionCode") String versionCode,

            @ApiParam(value = "差分包号）",required = true)
            @RequestParam("diffCode") String diffCode){

        Assert.notEmpty(appId, ResponseEnum.PARAMETER_ERROR);
        Assert.notEmpty(versionCode, ResponseEnum.PARAMETER_ERROR);

        AppInfo appInfo = appInfoService.getAppBsDiffByPackageName(appId,versionCode,diffCode);
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
            @ApiParam(value = "APP类型（0：Android 1：IOS 2: Android差分包）", required = true)
            @RequestParam("type") Integer type,
            @ApiParam(value = "是否更新（0：不更新  1：更新）", required = true)
            @RequestParam("hasUpdate") Boolean hasUpdate,
            @ApiParam(value = "是否可忽略该版本（是否强制更新）（0： 可忽略，1：不可取消）", required = true)
            @RequestParam("isIgnorable") Boolean isIgnorable,
            @ApiParam(value = "版本号", required = true)
            @RequestParam("versionCode") Integer versionCode,
            @ApiParam(value = "版本名", required = true)
            @RequestParam("versionName") String versionName,
            @ApiParam(value = "差分包号", required = false)
            @RequestParam("diffCode") String diffCode,
            @ApiParam(value = "更新内容", required = true)
            @RequestParam("updateContent") String updateContent,
            @ApiParam(value = "应用包名", required = true)
            @RequestParam("appId") String appId) {

        AppDTO appDTO = fileInfoService.uploadApp(file, MinIoUtil.BUCKET_NAME,"app");
        if(appDTO != null){
            if(type == 0 || type == 1) {
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
            } else if(type == 2) {
                Assert.notEmpty(diffCode, ResponseEnum.PARAMETER_ERROR);

                appDTO.setType(type);
                appDTO.setHasUpdate(hasUpdate);
                appDTO.setIsIgnorable(isIgnorable);
                appDTO.setVersionCode(versionCode);
                appDTO.setVersionName(versionName);
                appDTO.setUpdateContent(updateContent);
                appDTO.setAppId(appId);
                appDTO.setDiffCode(diffCode);

                boolean result = appInfoService.saveAppInfo(appDTO);
                if(result){
                    return R.ok().message("APP上传成功");
                }
            } else {
                return R.error().message("APP上传失败, type 无效！");
            }
        }
        return R.error().message("APP上传失败");
    }



}

