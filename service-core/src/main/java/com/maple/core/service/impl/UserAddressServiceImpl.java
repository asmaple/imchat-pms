package com.maple.core.service.impl;

import com.maple.core.pojo.entity.UserAddress;
import com.maple.core.mapper.UserAddressMapper;
import com.maple.core.service.UserAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户地址 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements UserAddressService {

}
