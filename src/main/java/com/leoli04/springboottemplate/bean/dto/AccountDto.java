package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 账号
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:35
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    /**
     * 邮箱
     * */
    private String email;

    /**
     * 姓氏
     * */
    private String firstName;

    /**
     * 名字
     * */
    private String lastName;

    /**
     * 电话
     * */
    private String telephone;

    /**
     * 公司名称
     * */
    private String companyName;

    /**
     * 语言
     */
    private String language;

    /**
     * 国家
     */
    private String country;
    /**
     * 城市
     */
    private String city;

    /**
     * IP
     * */
    private String ip;

    /**
     * 时区
     * */
    private String timeZone;

    /**
     * 关联crm客户 id
     */
    private String crmAccountId;
}
