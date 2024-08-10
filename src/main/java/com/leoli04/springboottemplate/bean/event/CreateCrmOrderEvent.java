package com.leoli04.springboottemplate.bean.event;

import com.leoli04.springboottemplate.bean.dto.OrderDto;

/**
 * @Description: 创建crm 销售订单事件类
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:46
 * @Version: 1.0
 */
public class CreateCrmOrderEvent extends BaseEvent<OrderDto> {
    /**
     * @param source    最初触发该事件的对象
     * @param eventData 该类型时间携带的信息
     */
    public CreateCrmOrderEvent(Object source, OrderDto eventData) {
        super(source, eventData);
    }
}
