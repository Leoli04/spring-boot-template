package com.leoli04.springboottemplate.service;

import com.leoli04.springboottemplate.bean.PageResult;
import com.leoli04.springboottemplate.bean.dto.PageSearchDto;

/**
 * @Description: demo service
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:59
 * @Version: 1.0
 */
public interface DemoService {

    PageResult pageSearch(PageSearchDto dto);
}
