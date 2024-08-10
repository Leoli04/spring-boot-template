package com.leoli04.springboottemplate.bean.event;

import com.leoli04.springboottemplate.bean.dto.CreateCrmLeadsDto;

/**
 * @Description: 创建crm线索事件类
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:38
 * @Version: 1.0
 */
public class CreateCrmLeadEvent extends BaseEvent<CreateCrmLeadsDto> {

    /**
     * @param source    最初触发该事件的对象
     * @param eventData 该类型时间携带的信息
     */
    public CreateCrmLeadEvent(Object source, CreateCrmLeadsDto eventData) {
        super(source, eventData);
    }
}
