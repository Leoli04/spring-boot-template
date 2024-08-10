package com.leoli04.springboottemplate.bean.event;

import com.leoli04.springboottemplate.bean.dto.CreateCrmAccountsDto;

/**
 * @Description: 创建crm 账号 事件类
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:27
 * @Version: 1.0
 */
public class CreateCrmAccountEvent extends BaseEvent<CreateCrmAccountsDto> {
    public CreateCrmAccountEvent(Object source, CreateCrmAccountsDto eventData) {
        super(source, eventData);
    }
}
