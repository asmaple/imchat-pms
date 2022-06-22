package com.maple.core.service.impl;

import com.maple.core.pojo.entity.Group;
import com.maple.core.mapper.GroupMapper;
import com.maple.core.service.GroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements GroupService {

}
