package com.leoli04.springboottemplate.common.consts;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: 订单常量
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 18:14
 * @Version: 1.0
 */
public interface OrderConst {
    /**
     * 订单状态
     */
    @Getter
    enum OrderStatusEnum{
        IMPERFECT("1", "待完善","CREATED"),
        UNPAID("2", "未支付","Processing"),
        PAID("3", "已支付","COMPLETED"),
        SHIPPED("4", "已发货"),
        DELIVERED("5", "已签收"),
        CANCELLED("6", "取消订单"),
        REFUND("7", "退款");

        private String code;
        private String name;
        private String zohoCode;

        OrderStatusEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        OrderStatusEnum(String code, String name,String zohoCode) {
            this.code = code;
            this.name = name;
            this.zohoCode = zohoCode;
        }

        public static String getDefaultNameByCode(String code) {
            if(StringUtils.isBlank(code)){
                return IMPERFECT.getName();
            }
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode().equals(code)) {
                    return orderStatusEnum.getName();
                }
            }
            return IMPERFECT.getName();
        }

    }


    /**
     * 订单支付方式
     */
    @Getter
    @AllArgsConstructor
    enum PaymentMethodEnum {
        ALIPAY("ALIPAY", "支付宝"),
        WECHAT("WECHAT", "微信"),
        PAYPAL("paypal", "paypal"),
        /**
         * 电汇
         */
        WIRE_TRANSFER("wire transfer", "电汇"),
        OTHER("OTHER", "其他"),

        ;

        private final String code;
        private final String name;

        public static String getNameByCode(String code) {
            for (PaymentMethodEnum countryCodeEnum : values()) {
                if (countryCodeEnum.getCode().equals(code)) {
                    return countryCodeEnum.getName();
                }
            }
            return null;
        }
        public static String getCodeByName(String name) {
            for (PaymentMethodEnum countryCodeEnum : values()) {
                if (countryCodeEnum.getName().equals(name)) {
                    return countryCodeEnum.getCode();
                }
            }
            return null;
        }

    }
}
