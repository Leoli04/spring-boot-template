package com.leoli04.springboottemplate.repository.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leoli04.springboottemplate.repository.dao.entity.Task;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 任务dao
 * @Author: LeoLi04
 * @CreateDate: 2024/11/1 10:22
 * @Version: 1.0
 */
@Repository
public interface TaskDao extends BaseMapper<Task> {

    List<Task> getLastTaskStatusByType(@Param("taskTypeList") List<String> taskTypeList);
}
