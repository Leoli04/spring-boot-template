package com.leoli04.springboottemplate.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Description: crm order dto
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:47
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    /**
     * 订单号
     */
    private String orderCode;

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
     * 国家/地区
     */
    private String country;

    /**
     * 结束地址
     * */
    private String meilStop;

    /**
     * 街道地址1
     * */
    private String addressLineOne;

    /**
     * 街道地址2
     * */
    private String addressLineTwo;

    /**
     * 城市
     * */
    private String city;

    /**
     * 州
     * */
    private String state;

    /**
     * 邮编
     * */
    private String postalCode;

    /**
     * 商品总金额
     */
    private BigDecimal subtotal;

    /**
     * 预估邮费
     */
    private BigDecimal estimateShipping;

    /**
     * 支付手续费
     */
    private BigDecimal paymentFee;

    /**
     * 总金额
     */
    private BigDecimal total;

    /**
     * 税费
     */
    private BigDecimal taxes;

    /**
     * 实际邮费
     */
    private BigDecimal actualShipping;

    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;

    /**
     * 实付款
     */
    private BigDecimal actualPayment;

    /**
     * 订单状态（1：待完善，2：未支付，3：已支付，4：取消，5：退款）
     */
    private String status;

    /**
     * 支付方式（1：paypal，2：wire transfer）
     */
    private String paymentMethod;

    /**
     * 支付时间
     */
    private LocalDateTime paymentTime;


    /**
     * 交易id
     */
    private String transactionId;


    /**
     * 产品集合
     */
    private List<OrderProductDto> productList;
}
