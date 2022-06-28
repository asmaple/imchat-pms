package com.maple.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.base.util.JwtUtils;
import com.maple.common.exception.Assert;
import com.maple.common.result.R;
import com.maple.common.result.ResponseEnum;
import com.maple.common.util.MD5;
import com.maple.core.pojo.dto.UserInfoDTO;
import com.maple.core.pojo.entity.User;
import com.maple.core.mapper.UserMapper;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.pojo.vo.LoginVO;
import com.maple.core.pojo.vo.RegisterVO;
import com.maple.core.service.UserLoginRecordService;
import com.maple.core.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 客户端用户 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserLoginRecordService userLoginRecordService;

    @Transactional(rollbackFor = Exception.class)  // 开启事务，失败回滚
    @Override
    public User register(RegisterVO registerVO, String ip) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username", registerVO.getUsername())
                .or()
                .eq("phone",registerVO.getPhone());

        Integer count = baseMapper.selectCount(userQueryWrapper);
        log.info(registerVO.getUsername() + ":是否注册 ==count=>>" + count);

        Assert.isTrue(count == 0, ResponseEnum.ACCOUNT_EXIST_ERROR);

        User user = new User();
        user.setUsername(registerVO.getUsername());
        user.setPassword(MD5.encrypt(registerVO.getPassword()));
        user.setPhone(registerVO.getPhone());
        user.setEmail(registerVO.getEmail());
        user.setIp(ip);
        user.setNickname(User.NICKNAME_VALUE);

        // 设置状态
        user.setStatus(User.STATUS_NORMAL);
        user.setSign(User.SIGN_VALUE);
        user.setDeleted(false);
        int insert = baseMapper.insert(user);
        log.info(registerVO.getUsername() + ":注册 ==insert=>>" + insert);
        if(insert > 0) {
            return  user;
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)  // 开启事务，失败回滚
    @Override
    public UserInfoDTO login(LoginVO loginVO, String ip) {
        String username = loginVO.getUsername();
        String password = loginVO.getPassword();

        //用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper
                .eq("username", username)
                .or()
                .eq("phone",username);

        User user = baseMapper.selectOne(userQueryWrapper);
        Assert.notNull(user, ResponseEnum.ACCOUNT_NOT_FOUNT);

        //密码是否正确
        Assert.equals(MD5.encrypt(password), user.getPassword(), ResponseEnum.LOGIN_ERROR);

        //用户是否被禁用
        Assert.equals(user.getStatus(), User.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);

        // 先获取最后一次登录记录
        UserLoginRecord lastUserLoginRecord = userLoginRecordService.getLastLoginRecord(user.getId());
        //记录登录日志
        userLoginRecordService.saveLoginRecord(user.getId(),user.getUsername(),user.getPhone(),ip);
        //生成token
        String token = JwtUtils.createToken(user.getId(), user.getUsername());
        //组装UserInfoDTO
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setToken(token);
        userInfoDTO.setUserId(user.getId().toString());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setNickname(user.getNickname());
        userInfoDTO.setPhone(user.getPhone());
        userInfoDTO.setGender(user.getGender());
        userInfoDTO.setAvatarUrl(user.getAvatarUrl());
        userInfoDTO.setOpenid(user.getOpenid());
        userInfoDTO.setEmail(user.getEmail());
        if(lastUserLoginRecord != null) {
            userInfoDTO.setLastIp(lastUserLoginRecord.getIp());
        }
        //返回
        return userInfoDTO;
    }

    @Override
    public IPage<User> listPage(Page<User> pageParam, String keyword) {
        if(StringUtils.isBlank(keyword)){
            return baseMapper.selectPage(pageParam, null);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .like("username", keyword)
                .or()
                .like("phone", keyword)
                .orderByDesc("id");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }




}
