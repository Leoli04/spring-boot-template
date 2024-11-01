package com.leoli04.springboottemplate.common.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 任务常量
 * @Author: LeoLi04
 * @CreateDate: 2024/11/1 10:10
 * @Version: 1.0
 */
public interface TaskConst {

    @Getter
    @AllArgsConstructor
    enum TaskTypeEnum {
        DEMO_IMPORT("demo_import","示例导入"),
        ;
        private String taskType;
        private String taskName;

        public static TaskTypeEnum getByTaskType(String taskType) {
            for (TaskTypeEnum taskTypeEnum : TaskTypeEnum.values()) {
                if (taskTypeEnum.getTaskType().equals(taskType)) {
                    return taskTypeEnum;
                }
            }
            return null;
        }
    }

    @Getter
    @AllArgsConstructor
    enum TaskStatusEnum {
        TASK_DOING("doing","处理中"),
        TASK_DONE("done","处理中"),

        ;
        private String taskStatus;
        private String statusDesc;
    }
}
