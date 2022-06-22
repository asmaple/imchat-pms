package com.maple.core.service.impl;

import com.maple.core.pojo.entity.Books;
import com.maple.core.mapper.BooksMapper;
import com.maple.core.service.BooksService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 联系人 服务实现类
 * </p>
 *
 * @author ggq
 * @since 2022-06-22
 */
@Service
public class BooksServiceImpl extends ServiceImpl<BooksMapper, Books> implements BooksService {

}
