package com.maple.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.core.pojo.dto.AppDTO;
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


    @Override
    public AppInfo getAppInfoByPackageName(String appId, String appType) {
        QueryWrapper<AppInfo> appInfoQueryWrapper = new QueryWrapper<>();

        appInfoQueryWrapper
                .eq("app_id", appId)
                .eq("type",appType)
                .orderByDesc("version_code")
                .last("limit 1");
        AppInfo appInfo = baseMapper.selectOne(appInfoQueryWrapper);
        return appInfo;
    }

    @Override
    public boolean saveAppInfo(AppDTO appDTO) {
        AppInfo appInfo = new AppInfo();
        BeanUtil.copyProperties(appDTO,appInfo);
        int count = baseMapper.insert(appInfo);
        return count > 0;
    }
}
