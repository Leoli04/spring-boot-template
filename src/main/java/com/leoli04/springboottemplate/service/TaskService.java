package com.leoli04.springboottemplate.service;

import com.leoli04.springboottemplate.common.consts.TaskConst;

import java.util.List;
import java.util.Map;

/**
 * @Description: task 服务
 * @Author: LeoLi04
 * @CreateDate: 2024/10/22 15:08
 * @Version: 1.0
 */
public interface TaskService {
    /**
     * 获取任务最新一次状态
     * @param taskTypeList
     * @return
     */
    Map<String,String> getLastTaskStatusByType(List<String> taskTypeList);

    /**
     * 保存任务
     * @param taskTypeEnum
     * @param taskStatusEnum
     * @return
     */
    Long saveTask(TaskConst.TaskTypeEnum taskTypeEnum, TaskConst.TaskStatusEnum taskStatusEnum);

    /**
     * 更新任务状态
     * @param taskId
     * @param taskStatusEnum
     * @return
     */
    Boolean updateTaskStatus(Long taskId, TaskConst.TaskStatusEnum taskStatusEnum);
}
