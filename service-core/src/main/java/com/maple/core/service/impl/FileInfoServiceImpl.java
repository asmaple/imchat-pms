package com.maple.core.service.impl;

import com.maple.core.pojo.entity.FileInfo;
import com.maple.core.mapper.FileInfoMapper;
import com.maple.core.service.FileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
