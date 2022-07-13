package com.maple.core.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.maple.common.result.R;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageUtils {

    private PageUtils() {}

    /**
     * 封装分页数据
     * @param pageModel
     * @param <T> 指定泛型
     * @return 返回包装过的分页数据
     */
    public static <T> R page(Long current,IPage<T> pageModel)  {
//            PageHelper.getStart(new Long(pageModel.getCurrent()).intValue(),new Long(pageModel.getSize()).intValue());
        return R.ok().data("list",pageModel.getRecords()).data("current",current).data("size",pageModel.getSize()).data("pages",pageModel.getPages()).data("total",pageModel.getTotal());
    }
}
