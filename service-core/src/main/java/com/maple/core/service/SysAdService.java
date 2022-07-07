package com.maple.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.maple.core.pojo.entity.SysAd;
import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.core.pojo.vo.SysAdVO;

/**
 * <p>
 * 广告 服务类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
public interface SysAdService extends IService<SysAd> {

    boolean saveSysAd(SysAdVO sysAdVO);

    IPage<SysAd> listPage(Page<SysAd> pageParam, String keyword, Integer type);
}
