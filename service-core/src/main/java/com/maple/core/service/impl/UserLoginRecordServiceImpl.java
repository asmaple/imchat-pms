package com.maple.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public UserLoginRecord getLastLoginRecord(Long userId) {
        log.info("登录记录--userId-->{}",userId);
        QueryWrapper<UserLoginRecord> userLoginRecordQueryWrapper = new QueryWrapper<>();
        userLoginRecordQueryWrapper.eq("user_id", userId)
                .orderByDesc("id")
                .last("limit 1");
        return baseMapper.selectOne(userLoginRecordQueryWrapper);
    }

    @Override
    public boolean saveLoginRecord(Long userId, String username, String phone, String ip) {
        //记录登录日志
        UserLoginRecord userLoginRecord = new UserLoginRecord();
        userLoginRecord.setUserId(userId);
        userLoginRecord.setUsername(username);
        userLoginRecord.setPhone(phone);
        userLoginRecord.setIp(ip);
        int count = baseMapper.insert(userLoginRecord);
        return count > 0;
    }

    @Override
    public IPage<UserLoginRecord> listPage(Page<UserLoginRecord> pageParam, String keyword) {
        if(StringUtils.isBlank(keyword)){
            return baseMapper.selectPage(pageParam, null);
        }

        QueryWrapper<UserLoginRecord> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("username", keyword)
                .or()
                .like("phone", keyword)
                .orderByDesc("id");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
