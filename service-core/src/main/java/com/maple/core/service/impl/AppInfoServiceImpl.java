package com.maple.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.dto.AppDTO;
import com.maple.core.pojo.entity.Admin;
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
    public AppInfo getAppBsDiffByPackageName(String appId, String versionCode, String diffCode) {
        QueryWrapper<AppInfo> appInfoQueryWrapper = new QueryWrapper<>();
        appInfoQueryWrapper
                .eq("app_id", appId)
                .eq("type","2")
                .eq("diff_code",diffCode)
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

    @Override
    public IPage<AppInfo> listPage(Page<AppInfo> pageParam, String keyword) {
        if(StringUtils.isBlank(keyword)){
            return baseMapper.selectPage(pageParam, null);
        }

        QueryWrapper<AppInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("version_code");

        return baseMapper.selectPage(pageParam, queryWrapper);
    }

}
