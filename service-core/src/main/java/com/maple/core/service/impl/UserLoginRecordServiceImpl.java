package com.maple.core.service.impl;

import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.mapper.UserLoginRecordMapper;
import com.maple.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

}
