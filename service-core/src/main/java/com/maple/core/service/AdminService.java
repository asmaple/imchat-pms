package com.maple.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.dto.AdminDTO;
import com.maple.core.pojo.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;

/**
 * <p>
 * 管理系统用户 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface AdminService extends IService<Admin> {

    boolean register(RegisterVO registerVO, String ip);

    AdminDTO login(LoginVO loginVO, String ip);

    IPage<Admin> listPage(Page<Admin> pageParam, String keyword);
}
