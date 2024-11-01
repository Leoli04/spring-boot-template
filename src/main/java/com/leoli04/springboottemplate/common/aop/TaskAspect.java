package com.leoli04.springboottemplate.common.aop;

import com.leoli04.springboottemplate.common.consts.TaskConst;
import com.leoli04.springboottemplate.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 任务注解切面
 * @Author: LeoLi04
 * @CreateDate: 2024/9/10 9:35
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class TaskAspect {

    @Autowired
    private TaskService taskService;

    /**
     * 以注解为切点
     */
    @Pointcut("@annotation(com.leoli04.springboottemplate.common.aop.Task)")
    public void taskHandle() {
    }

    @Around("taskHandle()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object proceed = null;
        if(method.isAnnotationPresent(Task.class)){
            Task task =  method.getAnnotation(Task.class);
            TaskConst.TaskTypeEnum taskTypeEnum = task.taskType();
            if(taskTypeEnum == null){
                log.error("TaskAspect#doAround 未定义任务类型={}",taskTypeEnum);
                return null;
            }
            Long taskId = taskService.saveTask(taskTypeEnum, TaskConst.TaskStatusEnum.TASK_DOING);
            try {
                proceed = joinPoint.proceed();
            } catch (Exception e) {
                log.error("TaskAspect#doAround 切面内容处理失败",e);
            }
            taskService.updateTaskStatus(taskId, TaskConst.TaskStatusEnum.TASK_DONE);
        }else{
            proceed = joinPoint.proceed();
        }
        return proceed;
    }

}
