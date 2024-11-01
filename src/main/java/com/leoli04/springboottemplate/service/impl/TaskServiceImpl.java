package com.leoli04.springboottemplate.service.impl;


import com.leoli04.springboottemplate.common.consts.TaskConst;
import com.leoli04.springboottemplate.repository.dao.TaskDao;
import com.leoli04.springboottemplate.repository.dao.entity.Task;
import com.leoli04.springboottemplate.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: task 服务
 * @Author: LeoLi04
 * @CreateDate: 2024/10/22 15:09
 * @Version: 1.0
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;


    @Override
    public Map<String, String> getLastTaskStatusByType(List<String> taskTypeList) {
        return Optional.ofNullable(taskDao.getLastTaskStatusByType(taskTypeList)).orElse(Lists.newArrayList())
                .stream()
                .collect(Collectors.toMap(Task::getTaskType,Task::getTaskStatus,(v1,v2)->v2));
    }

    @Override
    public Long saveTask(TaskConst.TaskTypeEnum taskTypeEnum, TaskConst.TaskStatusEnum taskStatusEnum) {
        Task task = new Task();
        task.setTaskType(taskTypeEnum.getTaskType());
        task.setTaskStatus(taskStatusEnum.getTaskStatus());
        taskDao.insert(task);
        return task.getId();
    }

    @Override
    public Boolean updateTaskStatus(Long taskId, TaskConst.TaskStatusEnum taskStatusEnum) {
        Task task = new Task();
        task.setId(taskId);
        task.setTaskStatus(taskStatusEnum.getTaskStatus());
        // 以当前时间为完成时间
        task.setCompletedTime(LocalDateTime.now());
        return taskDao.updateById(task) > 0;
    }
}
