package com.maple.core.service;

import com.maple.core.pojo.dto.AppDTO;
import com.maple.core.pojo.dto.FileDTO;
import com.maple.core.pojo.entity.FileInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 文件 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface FileInfoService extends IService<FileInfo> {

    FileDTO uploadFile(MultipartFile file, String bucketName, String moduleName);

    String queryBucketPolicy(String bucketName);

    boolean removeFile(String bucketName, String fileName);

    boolean removeFiles(String bucketName, String keys);

    boolean removeBucket(String bucketName);

    boolean downloadFile(HttpServletResponse httpServletResponse, String bucketName, String fileName);

    AppDTO uploadApp(MultipartFile file, String bucketName, String app);

}
