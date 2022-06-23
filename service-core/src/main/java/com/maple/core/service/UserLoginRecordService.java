package com.maple.core.service;

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

    UserLoginRecord getLastLoginRecord(Long id);

    boolean saveLoginRecord(Long id, String ip);
}
