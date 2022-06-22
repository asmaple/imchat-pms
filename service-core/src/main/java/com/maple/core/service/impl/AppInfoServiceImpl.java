package com.maple.core.service.impl;

import com.maple.core.pojo.entity.AppInfo;
import com.maple.core.mapper.AppInfoMapper;
import com.maple.core.service.AppInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用程序文件 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

}
