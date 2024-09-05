package com.leoli04.springboottemplate.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description: 分页结果
 * @Author: LeoLi04
 * @CreateDate: 2024/9/5 22:13
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private List<T> list;

    private Long total;
}
