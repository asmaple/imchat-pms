package com.maple.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.entity.AppInfo;
import com.maple.core.pojo.entity.SysAd;
import com.maple.core.mapper.SysAdMapper;
import com.maple.core.pojo.entity.UserLoginRecord;
import com.maple.core.pojo.vo.SysAdVO;
import com.maple.core.service.SysAdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 广告 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class SysAdServiceImpl extends ServiceImpl<SysAdMapper, SysAd> implements SysAdService {

    @Override
    public boolean saveSysAd(SysAdVO sysAdVO) {
        SysAd sysAd = new SysAd();
        BeanUtil.copyProperties(sysAdVO,sysAd);
        int count = baseMapper.insert(sysAd);
        return count > 0;
    }

    @Override
    public IPage<SysAd> listPage(Page<SysAd> pageParam, String keyword, Integer type) {
        if(StringUtils.isBlank(keyword)){
            return baseMapper.selectPage(pageParam, null);
        }

        QueryWrapper<SysAd> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("type", type)
                .orderByDesc("id");
        return baseMapper.selectPage(pageParam, queryWrapper);
    }
}
