package com.maple.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.maple.common.exception.Assert;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.dto.FileDTO;
import com.maple.core.pojo.entity.FileInfo;
import com.maple.core.mapper.FileInfoMapper;
import com.maple.core.service.FileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.core.utils.MinIoUtil;
import io.minio.Result;
import io.minio.messages.DeleteError;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 文件 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileInfoService {

    @Resource
    private MinIoUtil minIoUtil;


    @Override
    public FileDTO uploadFile(MultipartFile file, String bucketName, String moduleName) {
        Assert.notNull(file, ResponseEnum.UPLOAD_ERROR);
        Assert.notEmpty(bucketName, ResponseEnum.UPLOAD_ERROR);
        Assert.notEmpty(moduleName, ResponseEnum.UPLOAD_ERROR);
        try {
            FileDTO fileDTO = minIoUtil.uploadFile(file, bucketName,moduleName);
            // 插入到数据库中
            if(fileDTO != null){
                FileInfo fileInfo = new FileInfo();
                BeanUtil.copyProperties(fileDTO,fileInfo);
                int count = baseMapper.insert(fileInfo);
                if(count > 0){
                    return fileDTO;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=======uploadFile======>>>" + e.getMessage());
        }
        return null;
    }

    @Override
    public String queryBucketPolicy(String bucketName) {
        Assert.notEmpty(bucketName, ResponseEnum.PARAMETER_ERROR);
        try {
            return minIoUtil.getBucketPolicy(bucketName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean removeFile(String bucketName, String fileName) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(fileName, ResponseEnum.DOWNLOAD_ERROR);
        try {
            minIoUtil.removeObject(bucketName, fileName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=======removeFile======>>>" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeFiles(String bucketName, String keys) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(keys,ResponseEnum.DOWNLOAD_ERROR);
        try {
            String[] arrays = keys.split(",");
            List<String> keyList = Stream.of(arrays).collect(Collectors.toList());
            Iterable<Result<DeleteError>> iterable = minIoUtil.removeObjects(bucketName, keyList);
            if(getIterableCount(iterable) <= 0) {
                return true;
            }
        }catch (Exception e){
            e.getStackTrace();
            log.error("=======removeFiles======>>>" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean removeBucket(String bucketName) {
        Assert.notEmpty(bucketName, ResponseEnum.PARAMETER_ERROR);
        try {
            minIoUtil.removeBucket(bucketName);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("=======removeBucket======>>>" + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean downloadFile(HttpServletResponse httpServletResponse, String bucketName, String fileName) {
        Assert.notEmpty(bucketName, ResponseEnum.DOWNLOAD_ERROR);
        Assert.notEmpty(fileName, ResponseEnum.DOWNLOAD_ERROR);
        return minIoUtil.downloadFile(httpServletResponse, bucketName, fileName);
    }


    private static int getIterableCount(Iterable<?> i){
        int count = 0;
        Iterator<?> it = i.iterator();
        while (it.hasNext()) {
            it.next();
            count++;
        }
        return count;
    }
}
