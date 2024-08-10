package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description: 订单中的产品信息dto
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:51
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderProductDto {

    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 产品编号
     */
    private Long productNum;

    /**
     * 产品型号
     */
    private String productCode;

    /**
     * 品牌
     */
    private String manufacturer;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 总价
     */
    private BigDecimal extendedPrice;
    /**
     * 货币（USD：美元，CNY：人民币）
     */
    private String currency;
}
