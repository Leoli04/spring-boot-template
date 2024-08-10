package com.leoli04.springboottemplate.bean.event;

import org.springframework.context.ApplicationEvent;

/**
 * @Description: 事件类统一的父类
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:11
 * @Version: 1.0
 */
public abstract class BaseEvent<T> extends ApplicationEvent {

    private T eventData;

    /**
     * @param source 最初触发该事件的对象
     * @param eventData 该类型时间携带的信息
     */
    public BaseEvent(Object source, T eventData) {
        super(source);
        this.eventData = eventData;
    }

    public T getEventData() {
        return eventData;
    }
}
