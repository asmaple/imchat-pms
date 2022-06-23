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
    public boolean registerInfo(Long id) {
        //判断用户是否已被注册
        log.info("用户id---->>>{}",id);
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("user_id", id);
        Integer count = baseMapper.selectCount(userInfoQueryWrapper);
        Assert.isTrue(count == 0, ResponseEnum.ACCOUNT_EXIST_ERROR);

        //插入用户信息记录：user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(id);
        int insert = baseMapper.insert(userInfo);
        return insert > 0;
    }
}
