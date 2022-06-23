package com.maple.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.mapper.UserLoginRecordMapper;
import com.maple.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Slf4j
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {

    @Override
    public UserLoginRecord getLastLoginRecord(Long id) {
        log.info("登录记录--userId-->{}",id);
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper.eq("user_id", id)
                .orderByDesc("id")
                .last("limit 1");
        return baseMapper.selectOne(userLoginRecordQueryWrapper);
    }

    @Override
    public boolean saveLoginRecord(Long id, String ip) {
        //记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(id);
        userLoginRecord.setIp(ip);
        int count = baseMapper.insert(userLoginRecord);
        return count > 0;
    }
}
