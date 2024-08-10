package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 创建crm 客户
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:28
 * @Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCrmAccountsDto implements Serializable {

    private AccountDto accout;
}
