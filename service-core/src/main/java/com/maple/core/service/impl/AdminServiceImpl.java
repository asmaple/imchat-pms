package com.maple.core.service.impl;

import com.maple.core.pojo.entity.Admin;
import com.maple.core.mapper.AdminMapper;
import com.maple.core.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理系统用户 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
