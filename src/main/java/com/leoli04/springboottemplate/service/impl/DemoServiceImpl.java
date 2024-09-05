package com.leoli04.springboottemplate.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.leoli04.springboottemplate.bean.PageResult;
import com.leoli04.springboottemplate.bean.dto.PageSearchDto;
import com.leoli04.springboottemplate.repository.dao.DemoDao;
import com.leoli04.springboottemplate.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: demo service impl
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:59
 * @Version: 1.0
 */
@Service
@Slf4j
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public PageResult pageSearch(PageSearchDto dto) {
        Page page = new Page(dto.getPageIndex(),dto.getPageSize());
        page.addOrder(dto.getIsAsc() ? OrderItem.asc(dto.getOrderBy()) : OrderItem.desc(dto.getOrderBy()));
        demoDao.pageList("",page);
        return null;
    }
}
