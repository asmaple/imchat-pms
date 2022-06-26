package com.maple.core.controller.admin;


import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.dto.FileDTO;
import com.maple.core.pojo.entity.Admin;
import com.maple.core.pojo.entity.User;
import com.maple.core.properties.MinioProperties;
import com.maple.core.service.AdminService;
import com.maple.core.service.FileInfoService;
import com.maple.core.utils.MinIoUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Api(tags = "文件接口")
@RestController
@RequestMapping("/admin/core/fileInfo")
@Slf4j
public class FileInfoController {

    @Resource
    private FileInfoService fileInfoService;

    @Resource
    private AdminService adminService;

    /**
     * 文件上传
     * @param file
//     * @param bucketName
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    @ResponseBody
    public R upload(
            @ApiParam(value= "文件", required = true)
            @RequestParam("file") MultipartFile file,

           // @ApiParam(value = "桶名", required = true)
           // @RequestParam("bucketName") String bucketName,

            @ApiParam(value = "模块名", required = true)
            @RequestParam("moduleName") String moduleName) {

        FileDTO fileDTO = fileInfoService.uploadFile(file, MinIoUtil.BUCKE_TNAME,moduleName);
        if(fileDTO != null){
            return R.ok().data("fileInfo",fileDTO).message("文件上传成功");
        }
        return R.error().message("文件上传失败");
    }

    /**
     * 下载文件
     * @param httpServletResponse
//     * @param bucketName
     * @param fileName
     */
    @ApiOperation("文件下载")
    @GetMapping("/download")
    public void download(HttpServletResponse httpServletResponse,
//                         @ApiParam(value = "桶名", required = true)
//                         @RequestParam("bucketName") String bucketName,
                         @ApiParam(value = "文件名(objectName)", required = true)
                         @RequestParam("fileName") String fileName) {
        boolean result = fileInfoService.downloadFile(httpServletResponse, MinIoUtil.BUCKE_TNAME,fileName);
        log.info("=====文件下载结果=====>>>" + result);
    }


    @ApiOperation("根据文件名删除文件")
    @PostMapping("/removeFile")
    public R removeFile(
//            @ApiParam(value = "桶名", required = true)
//            @RequestParam("bucketName") String bucketName,
            @ApiParam(value = "文件名(objectName)", required = true)
            @RequestParam("fileName") String fileName) {
        boolean result = fileInfoService.removeFile(MinIoUtil.BUCKE_TNAME,fileName);
        return result? R.ok(): R.error();
    }

    @ApiOperation("多文件删除")
    @PostMapping("/removeFiles")
    public R removeFiles(
//            @ApiParam(value = "桶名", required = true)
//            @RequestParam("bucketName") String bucketName,
            @ApiParam(value = "文件名以英文逗号分割(最后一条不带逗号)", required = true)
            @RequestParam("keys") String keys) {

//        String[] strArray = {"aaa","bbb","ccc"}；
//        String str= StringUtils.join(strArry,",");
//        System.out.println(str);
        boolean result = fileInfoService.removeFiles(MinIoUtil.BUCKE_TNAME,keys);
        return result? R.ok(): R.error();
    }

    @ApiOperation("根据桶名删除桶")
    @PostMapping("/removeBucket")
    public R removeBucket(
            @ApiParam(value = "桶名", required = true)
            @RequestParam("bucketName") String bucketName,
            HttpServletRequest request) {
        //获取当前登录用户的id
        String token = request.getHeader("token");
        Long mUserId = JwtUtils.getUserId(token);

        log.info("----removeBucket--->>>{}",mUserId);
        Admin admin = adminService.getById(mUserId);
        Assert.notNull(admin, ResponseEnum.ACCOUNT_NOT_FOUNT);
        // 用户被锁定
        Assert.equals(admin.getStatus(), User.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);
        // 无权限
        Assert.isTrue((admin.getRoleCode().intValue() == Admin.ROLE_ROOT.intValue()), ResponseEnum.ROLE_NOT_PERMISSION);

        boolean result = fileInfoService.removeBucket(bucketName);
        return result? R.ok(): R.error();
    }

    @ApiOperation("查看桶策略")
    @GetMapping("/getBucketPolicy")
    public R queryBucketPolicy(
            @ApiParam(value = "桶名", required = true)
            @RequestParam("bucketName") String bucketName) {
        String bucketPolicy = fileInfoService.queryBucketPolicy(bucketName);
        if(!StringUtils.isEmpty(bucketPolicy)){
            return R.ok().data("bucketPolicy",bucketPolicy);
        }
        return R.error();
    }
}

