package com.maple.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.dto.UserInfoDTO;
import com.maple.core.pojo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;

/**
 * <p>
 * 客户端用户 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface UserService extends IService<User> {

    User register(RegisterVO registerVO, String ip);

    UserInfoDTO login(LoginVO loginVO, String ip);

    IPage<User> listPage(Page<User> pageParam, String keyword);
}
