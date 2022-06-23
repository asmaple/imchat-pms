package com.maple.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.common.exception.Assert;
import com.maple.common.result.ResponseEnum;
import com.maple.core.pojo.entity.UserInfo;
import com.maple.core.mapper.UserInfoMapper;
import com.maple.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户详情 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Slf4j
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean registerInfo(Long userId) {
        //判断用户是否已被注册
        log.info("用户userId---->>>{}",userId);
//        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
//        userInfoQueryWrapper.eq("user_id", id);
//        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
//        Assert.isTrue(count == 0, ResponseEnum.ACCOUNT_EXIST_ERROR);

        UserInfo userInfo = baseMapper.selectById(userId);
        // 如果用户存在 则抛出异常 用户已存在
        Assert.isNull(userInfo,ResponseEnum.ACCOUNT_EXIST_ERROR);

        //插入用户信息记录：user_info
        UserInfo newUserInfo = new UserInfo();
        newUserInfo.setUserId(userId);
        int insert = baseMapper.insert(newUserInfo);
        return insert > 0;
    }
}
