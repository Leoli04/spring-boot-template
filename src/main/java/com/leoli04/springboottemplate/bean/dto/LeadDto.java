package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: crm 线索
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:44
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeadDto {

    /**
     * 线索自己后台id
     */
    private Long id;
    /**
     * 产品编号
     * */
    private Long productNum;


    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 产品型号
     */
    private String productCode;

    /**
     * 线索触发位置
     */
    private Integer clueLocation;

    /**
     * 数量
     * */
    private Integer quantity;

    /**
     * 备注
     * */
    private String notes;


    /**
     * 操作行为
     * */
    private String operationalBehavior;

    /**
     * 附件url
     */
    private String fileUrl;

}
