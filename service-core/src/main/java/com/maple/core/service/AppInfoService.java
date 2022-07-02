package com.maple.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.dto.AppDTO;
import com.maple.core.pojo.entity.AppInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 应用程序文件 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface AppInfoService extends IService<AppInfo> {

    AppInfo getAppInfoByPackageName(String appId, String appType);

    boolean saveAppInfo(AppDTO appDTO);

    IPage<AppInfo> listPage(Page<AppInfo> pageParam, String keyword);

    AppInfo getAppBsDiffByPackageName(String appId, String versionCode, String diffCode);
}
