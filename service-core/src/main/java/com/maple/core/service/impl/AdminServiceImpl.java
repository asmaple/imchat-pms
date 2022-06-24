package com.maple.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.MD5;
import com.maple.core.pojo.dto.AdminDTO;
import com.maple.core.pojo.dto.UserInfoDTO;
import com.maple.core.pojo.entity.Admin;
import com.maple.core.mapper.AdminMapper;
import com.maple.core.pojo.entity.User;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;
import com.maple.core.service.AdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.core.service.UserLoginRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 管理系统用户 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
@Slf4j
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Resource
    private UserLoginRecordService userLoginRecordService;


    @Transactional(rollbackFor = Exception.class)  // 开启事务，失败回滚
    @Override
    public boolean register(RegisterVO registerVO, String ip) {
        Admin admin = new Admin();
        admin.setUsername(registerVO.getUsername());
        admin.setPhone(registerVO.getPhone());
        admin.setEmail(registerVO.getEmail());
        admin.setPassword(MD5.encrypt(registerVO.getPassword()));
        admin.setSign(Admin.SIGN_VALUE);
        admin.setIp(ip);

        // 设置状态
        admin.setStatus(Admin.STATUS_NORMAL);
        admin.setSign(User.SIGN_VALUE);
        admin.setDeleted(false);

        // 设置角色
        if(registerVO.getUsername().equals("gaoguanqi")){
            admin.setRoleCode(Admin.ROLE_ROOT);
        } else {
            admin.setRoleCode(Admin.ROLE_USER);
        }
        int insert = baseMapper.insert(admin);
        log.info(registerVO.getUsername() + ":注册 ==insert=>>" + insert);
        return insert > 0;
    }

    @Transactional(rollbackFor = Exception.class)  // 开启事务，失败回滚
    @Override
    public AdminDTO login(LoginVO loginVO, String ip) {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();

        //用户是否存在
        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper
                .eq("username", username)
                .or()
                .eq("phone",username);

        Admin admin = baseMapper.selectOne(adminQueryWrapper);
        Assert.notNull(admin, ResponseEnum.ACCOUNT_NOT_FOUNT);
        //密码是否正确
        Assert.equals(MD5.encrypt(password), admin.getPassword(), ResponseEnum.LOGIN_ERROR);

        //用户是否被禁用
        Assert.equals(admin.getStatus(), User.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 先获取最后一次登录记录
        UserLoginRecord lastUserLoginRecord = userLoginRecordService.getLastLoginRecord(admin.getId());
        //记录登录日志
        userLoginRecordService.saveLoginRecord(admin.getId(),ip);
        //生成token
        String token = JwtUtils.createToken(admin.getId(), admin.getUsername());

        //组装AdminDTO
        AdminDTO adminDTO = new AdminDTO();
        adminDTO.setToken(token);
        adminDTO.setUsername(admin.getUsername());
        adminDTO.setPhone(admin.getPhone());
        adminDTO.setAvatarUrl(admin.getAvatarUrl());
        adminDTO.setEmail(admin.getEmail());
        if(lastUserLoginRecord != null) {
            adminDTO.setLastIp(lastUserLoginRecord.getIp());
        }
        //返回
        return adminDTO;
    }
}
