package com.leoli04.springboottemplate.repository.dao.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Description: 任务表
 * @Author: LeoLi04
 * @CreateDate: 2024/10/22 13:11
 * @Version: 1.0
 */
@Data
@TableName("ic_task")
public class Task extends BasicEntity{
    @TableField("task_type")
    private String taskType;
    @TableField("task_status")
    private String taskStatus;
    @TableField("completed_time")
    private LocalDateTime completedTime;
}
