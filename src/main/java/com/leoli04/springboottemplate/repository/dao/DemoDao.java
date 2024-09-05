package com.leoli04.springboottemplate.repository.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Description: dao
 * @Author: LeoLi04
 * @CreateDate: 2024/9/5 22:22
 * @Version: 1.0
 */
@Repository
public interface DemoDao {
    Page pageList(@Param("param1") String param1, @Param("page") Page page);
}
