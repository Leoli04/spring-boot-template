package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Description: 创建crm 线索,线索中包含客户信息 客户dto
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:39
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCrmLeadsDto implements Serializable {

    private static final long serialVersionUID = 6232071443593044505L;
    /**
     * 客户信息
     */
    private AccountDto account;

    /**
     * 线索信息
     */
    private LeadDto lead;
}
