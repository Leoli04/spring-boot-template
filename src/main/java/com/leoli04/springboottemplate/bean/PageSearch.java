package com.leoli04.springboottemplate.bean;

import lombok.Data;

/**
 * @Description: 分页搜索
 * @Author: LeoLi04
 * @CreateDate: 2024/8/29 9:23
 * @Version: 1.0
 */
@Data
public class PageSearch {
    private Integer pageSize = 10;
    private Integer pageIndex = 1;
    private String orderBy = "id";
    private Boolean isAsc = false;
}
