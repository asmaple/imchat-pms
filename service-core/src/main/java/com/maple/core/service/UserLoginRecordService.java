package com.maple.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户登录记录 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {

    UserLoginRecord getLastLoginRecord(Long userId);

    boolean saveLoginRecord(Long userId, String username, String phone, String ip);

    IPage<UserLoginRecord> listPage(Page<UserLoginRecord> pageParam, String keyword);
}
