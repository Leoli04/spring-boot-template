package com.leoli04.springboottemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.leoli04.springboottemplate.bean.dto.AccountDto;
import com.leoli04.springboottemplate.bean.dto.CreateCrmAccountsDto;
import com.leoli04.springboottemplate.bean.dto.CreateCrmLeadsDto;
import com.leoli04.springboottemplate.bean.dto.LeadDto;
import com.leoli04.springboottemplate.bean.dto.OrderDto;
import com.leoli04.springboottemplate.bean.dto.OrderProductDto;
import com.leoli04.springboottemplate.bean.event.CreateCrmAccountEvent;
import com.leoli04.springboottemplate.bean.event.CreateCrmLeadEvent;
import com.leoli04.springboottemplate.bean.event.CreateCrmOrderEvent;
import com.leoli04.springboottemplate.common.consts.OrderConst;
import com.leoli04.springboottemplate.repository.client.ZohoClient;
import com.zoho.crm.api.exception.SDKException;
import com.zoho.crm.api.record.ActionHandler;
import com.zoho.crm.api.record.Field;
import com.zoho.crm.api.record.LineItemProduct;
import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.util.APIResponse;
import com.zoho.crm.api.util.Choice;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: zoho service
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:03
 * @Version: 1.0
 */
@Service
@Slf4j
public class ZohoCrmService {

    @Autowired
    private ZohoClient zohoClient;

    @Async
    @EventListener(CreateCrmLeadEvent.class)
    public void createCrmLead(CreateCrmLeadEvent event) {
        CreateCrmLeadsDto eventData = event.getEventData();
        AccountDto account = eventData.getAccount();
        LeadDto lead = eventData.getLead();
        log.info("同步crm线索： account={},lead={}", JSON.toJSONString(account), JSON.toJSONString(lead));

        // 创建线索
        this.createCrmLeads(account, lead);
    }

    /**
     * zoho crm新增线索
     *
     * @param lead
     * @return
     * @throws Exception
     */
    private void createCrmLeads(AccountDto account, LeadDto lead) {
        // 创建线索
        String moduleName = ZohoClient.ZohoModule.LEADS.getModuleName();
        // 系统定义字段
        HashMap<Field, Object> fieldValueMap = Maps.newHashMap();
        fieldValueMap.put(Field.Leads.EMAIL, account.getEmail());
        fieldValueMap.put(Field.Leads.FIRST_NAME, account.getFirstName());
        String lastName = account.getLastName();
        fieldValueMap.put(Field.Leads.LAST_NAME, lastName);
        fieldValueMap.put(Field.Leads.COMPANY, account.getCompanyName());
        fieldValueMap.put(Field.Leads.COUNTRY, account.getCountry());
        fieldValueMap.put(Field.Leads.CITY, account.getCity());

        fieldValueMap.put(Field.Leads.PHONE, account.getTelephone());
        fieldValueMap.put(Field.Leads.DESCRIPTION, lead.getNotes());

        // 自定义字段
        Map<String, Object> keyValueMap = Maps.newHashMap();
        keyValueMap.put("ClueId", lead.getId().toString());
        keyValueMap.put("ProductNum", lead.getProductNum().toString());
        keyValueMap.put("manufacturer", lead.getManufacturer());
        keyValueMap.put("productCode", lead.getProductCode());
        keyValueMap.put("Quantity", lead.getQuantity());
        keyValueMap.put("FileUrl", lead.getFileUrl());
        keyValueMap.put("Ip", account.getIp());
        keyValueMap.put("TimeZone", account.getTimeZone());


        try {
            APIResponse<ActionHandler> response = zohoClient.insertRecords(moduleName, fieldValueMap, keyValueMap);
            zohoClient.handleZohoResponse(response);
        } catch (SDKException e) {
            log.error("createCrmLeads error.moduleName={}", moduleName, e);
        }
    }


    @Async
    @EventListener(CreateCrmAccountEvent.class)
    public void createCrmAccount(CreateCrmAccountEvent event) {
        CreateCrmAccountsDto eventData = event.getEventData();
        AccountDto accout = eventData.getAccout();
        log.info("同步crm客户： customer={}", JSON.toJSONString(accout));
        // 同步保存客户
        this.saveAccounts(accout);
    }

    /**
     * 同步客户到crm联系人
     * https://www.zoho.com/crm/developer/docs/api/v6/upsert-records.html
     */
    private void saveAccounts(AccountDto accout) {
        String moduleName = ZohoClient.ZohoModule.Accounts.getModuleName();
        // 系统定义字段
        HashMap<Field, Object> fieldValueMap = Maps.newHashMap();
        String accountName = accout.getCompanyName();
        fieldValueMap.put(Field.Accounts.ACCOUNT_NAME, accountName);
        fieldValueMap.put(Field.Accounts.PHONE, accout.getTelephone());
        // 自定义字段
        Map<String, Object> keyValueMap = Maps.newHashMap();
        keyValueMap.put("Email", accout.getEmail());
        keyValueMap.put("ContactName", this.getAccountName(accout.getFirstName(), accout.getLastName()));
        keyValueMap.put("country", accout.getCountry());
        keyValueMap.put("city", accout.getCity());
        keyValueMap.put("language", accout.getLanguage());
        keyValueMap.put("IP", accout.getIp());
        keyValueMap.put("TimeZone", accout.getTimeZone());

        List<String> duplicateCheckFields = Lists.newArrayList("Email");

        try {
            APIResponse<ActionHandler> response = zohoClient.upsertRecords(moduleName, fieldValueMap, keyValueMap, duplicateCheckFields);
            Long accountId = zohoClient.handleZohoResponse(response);
            if (null != accountId) {
                AccountDto accountDto = new AccountDto();
                accountDto.setEmail(accout.getEmail());
                accountDto.setCrmAccountId(accountId.toString());
                log.info("将crm 客户id 同步给 自己后台.accountDto={}", JSON.toJSONString(accountDto));
                // todo 实现
            }
        } catch (SDKException e) {
            log.error("saveContacts error.moduleName={}", moduleName, e);
        }
    }

    /**
     * 用firstName与lastName拼接客户账号名称
     *
     * @param firstName
     * @param lastName
     * @return
     */
    private String getAccountName(String firstName, String lastName) {
        if (StringUtils.isAllBlank(firstName, lastName)) {
            return "default";
        }
        String accountName = "";
        if (StringUtils.isNotBlank(firstName)) {
            accountName += firstName;
        }
        if (StringUtils.isNotBlank(lastName)) {
            accountName += (" " + lastName);
        }
        return accountName;
    }


    @Async
    @EventListener(CreateCrmOrderEvent.class)
    public void createCrmOrder(CreateCrmOrderEvent event) {
        OrderDto eventData = event.getEventData();
        log.info("同步crm订单： eventData={}", JSON.toJSONString(eventData));
        this.saveCrmOrder(eventData);
    }

    private void saveCrmOrder(OrderDto orderDTO) {
        String moduleName = ZohoClient.ZohoModule.SALES_ORDERS.getModuleName();
        // 系统定义字段
        HashMap<Field, Object> fieldValueMap = Maps.newHashMap();
        // 自定义字段
        Map<String, Object> keyValueMap = Maps.newHashMap();

        // 订单主体信息
        keyValueMap.put("OrderCode", orderDTO.getOrderCode());
        // 客户名称（公司名称）
        String email = orderDTO.getEmail();
        if (StringUtils.isNotBlank(email)) {
            keyValueMap.put("Email", email);
        }

        keyValueMap.put("FirstName", orderDTO.getFirstName());
        keyValueMap.put("LastName", orderDTO.getLastName());
        keyValueMap.put("Telephone", orderDTO.getTelephone());
        fieldValueMap.put(Field.Sales_Orders.STATUS, new Choice<String>(OrderConst.OrderStatusEnum.getDefaultNameByCode(orderDTO.getStatus())));
        keyValueMap.put("PaymentMethod", OrderConst.PaymentMethodEnum.getNameByCode(orderDTO.getPaymentMethod()));
        LocalDateTime paymentTime = orderDTO.getPaymentTime();
        if (paymentTime != null) {
            keyValueMap.put("PaymentTime", paymentTime.atZone(ZoneId.systemDefault()).toOffsetDateTime());
        }

        keyValueMap.put("TransactionId", orderDTO.getTransactionId());


        // 地址信息
        fieldValueMap.put(Field.Quotes.SHIPPING_COUNTRY, orderDTO.getCountry());
        keyValueMap.put("MeilStop", orderDTO.getMeilStop());
        fieldValueMap.put(Field.Quotes.SHIPPING_STREET, orderDTO.getAddressLineOne());
        keyValueMap.put("AddressLineTwo", orderDTO.getAddressLineTwo());
        fieldValueMap.put(Field.Quotes.SHIPPING_CITY, orderDTO.getCity());
        fieldValueMap.put(Field.Quotes.SHIPPING_STATE, orderDTO.getState());
        fieldValueMap.put(Field.Quotes.SHIPPING_CODE, orderDTO.getPostalCode());

        // 费用相关
        fieldValueMap.put(Field.Sales_Orders.SUB_TOTAL, orderDTO.getSubtotal());
        BigDecimal estimateShipping = orderDTO.getEstimateShipping();
        if (estimateShipping != null) {
            keyValueMap.put("EstimateShipping", estimateShipping.doubleValue());
        }
        BigDecimal paymentFee = orderDTO.getPaymentFee();
        if (paymentFee != null) {
            keyValueMap.put("PaymentFee", paymentFee.doubleValue());
        }
        // total 不用同步，通过计算获取
//        fieldValueMap.put(Field.Sales_Orders.TAX, orderDTO.getTaxes());
//        fieldValueMap.put(Field.Sales_Orders.DISCOUNT, orderDTO.getDiscountAmount());


        // 订单中的产品item
        List<OrderProductDto> productList = orderDTO.getProductList();
        if (CollectionUtils.isNotEmpty(productList)) {
            ArrayList<Record> inventoryLineItemList = new ArrayList<com.zoho.crm.api.record.Record>();
            for (OrderProductDto orderProductDTO : productList) {
                LineItemProduct lineItemProduct = new LineItemProduct();
                lineItemProduct.setId(216214000000471711L);
                com.zoho.crm.api.record.Record inventoryLineItem = new com.zoho.crm.api.record.Record();
                inventoryLineItem.addKeyValue("ProductCode", orderProductDTO.getProductCode());
                inventoryLineItem.addKeyValue("Manufacturer", orderProductDTO.getManufacturer());
                inventoryLineItem.addKeyValue("Quantity", orderProductDTO.getQuantity().doubleValue());
                inventoryLineItem.addKeyValue("List_Price", orderProductDTO.getUnitPrice().doubleValue());
//            inventoryLineItem.addKeyValue("Net_Total", orderProductDTO.getExtendedPrice());
                inventoryLineItem.addKeyValue("Price_Currency", orderProductDTO.getCurrency());
                inventoryLineItem.addKeyValue("Product_Name", lineItemProduct);
                inventoryLineItemList.add(inventoryLineItem);
            }
            fieldValueMap.put(Field.Sales_Orders.ORDERED_ITEMS, inventoryLineItemList);
        }
        // 唯一
        List<String> duplicateCheckFields = Lists.newArrayList("OrderCode");

        try {
            APIResponse<ActionHandler> response = zohoClient.upsertRecords(moduleName, fieldValueMap, keyValueMap, duplicateCheckFields);
            zohoClient.handleZohoResponse(response);
        } catch (SDKException e) {
            log.error("createCrmOrder error.moduleName={}", moduleName, e);
        }
    }
}
