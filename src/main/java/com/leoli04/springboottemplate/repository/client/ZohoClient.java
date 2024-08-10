package com.leoli04.springboottemplate.repository.client;

import com.alibaba.fastjson.JSON;
import com.zoho.crm.api.HeaderMap;
import com.zoho.crm.api.exception.SDKException;
import com.zoho.crm.api.record.APIException;
import com.zoho.crm.api.record.ActionHandler;
import com.zoho.crm.api.record.ActionResponse;
import com.zoho.crm.api.record.ActionWrapper;
import com.zoho.crm.api.record.BodyWrapper;
import com.zoho.crm.api.record.Field;
import com.zoho.crm.api.record.Record;
import com.zoho.crm.api.record.RecordOperations;
import com.zoho.crm.api.record.SuccessResponse;
import com.zoho.crm.api.util.APIResponse;
import com.zoho.crm.api.util.Model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: zoho 服务封装
 * @Author: LeoLi04
 * @CreateDate: 2024/8/10 17:04
 * @Version: 1.0
 */
@Repository
@Slf4j
public class ZohoClient {

    /**
     * 新增记录
     *
     * @param moduleAPIName 模块名称
     * @param fieldValueMap 系统定义字段
     * @param keyValueMap   自定义字段
     * @see ZohoModule
     */
    public APIResponse<ActionHandler> insertRecords(String moduleAPIName, Map<Field, Object> fieldValueMap, Map<String, Object> keyValueMap) throws SDKException {
        RecordOperations recordOperations = new RecordOperations(moduleAPIName);
        BodyWrapper bodyWrapper = this.getZohoBodyWrapper(fieldValueMap, keyValueMap);
        log.info("insertRecords:moduleAPIName={},bodyWrapper={}", moduleAPIName, JSON.toJSONString(bodyWrapper));
        return recordOperations.createRecords(bodyWrapper, new HeaderMap());
    }

    /**
     * 新增保存记录：系统根据检查字段的值检查重复记录。如果记录已存在，则会更新。如果没有，则插入记录。
     * 系统定义唯一字段：
     * Leads - Email
     * Contacts - Email
     * Products - Product_Name
     * SalesOrders - Subject
     *
     * @param moduleAPIName        模块名称
     * @param fieldValueMap        系统定义字段
     * @param keyValueMap          自定义字段
     * @param duplicateCheckFields 系统定义唯一字段
     * @see ZohoModule
     */
    public APIResponse<ActionHandler> upsertRecords(String moduleAPIName, Map<Field, Object> fieldValueMap, Map<String, Object> keyValueMap, List<String> duplicateCheckFields) throws SDKException {
        RecordOperations recordOperations = new RecordOperations(moduleAPIName);
        BodyWrapper bodyWrapper = this.getZohoBodyWrapper(fieldValueMap, keyValueMap);
        if (CollectionUtils.isNotEmpty(duplicateCheckFields)) {
            bodyWrapper.setDuplicateCheckFields(duplicateCheckFields);
        }
        log.info("upsertRecords:moduleAPIName={},bodyWrapper={}", moduleAPIName, JSON.toJSONString(bodyWrapper));
        return recordOperations.upsertRecords(bodyWrapper, new HeaderMap());
    }

    /**
     * 组装zoho BodyWrapper
     *
     * @param fieldValueMap
     * @param keyValueMap
     * @return
     */
    public BodyWrapper getZohoBodyWrapper(Map<Field, Object> fieldValueMap, Map<String, Object> keyValueMap) {
        BodyWrapper bodyWrapper = new BodyWrapper();
        List<Record> records = new ArrayList<Record>();
        com.zoho.crm.api.record.Record record = new com.zoho.crm.api.record.Record();
        if (MapUtils.isNotEmpty(fieldValueMap)) {
            fieldValueMap.forEach((key, value) -> {
                record.addFieldValue(key, value);
            });
        }
        if (MapUtils.isNotEmpty(keyValueMap)) {
            keyValueMap.forEach((key, value) -> {
                record.addKeyValue(key, value);
            });
        }
        records.add(record);
        bodyWrapper.setData(records);
        return bodyWrapper;
    }

    /**
     * {
     *     "expected": true,
     *     "headers": {
     *         "Server": "ZGS",
     *         "X-Content-Type-Options": "nosniff",
     *         "Connection": "keep-alive",
     *         "Pragma": "no-cache",
     *         "clientVersion": "8794360",
     *         "Date": "Thu, 01 Aug 2024 10:21:35 GMT",
     *         "Referrer-Policy": "strict-origin",
     *         "X-ACCESSTOKEN-RESET": "2024-08-01T19:21:19+08:00",
     *         "Strict-Transport-Security": "max-age=63072000",
     *         "Cache-Control": "no-store, no-cache, must-revalidate, private",
     *         "Content-Security-Policy": "default-src 'none';frame-ancestors 'self'; report-uri https://logsapi.zoho.com/csplog?service=crm",
     *         "Content-Disposition": "attachment; filename=response.json",
     *         "Set-Cookie": "_zcsr_tmp=e238e5d5-7d01-4aee-87c0-3e4f7644dd5c;path=/;SameSite=Strict;Secure;priority=high",
     *         "Expires": "Thu, 01 Jan 1970 00:00:00 GMT",
     *         "Content-Length": "369",
     *         "X-XSS-Protection": "1; mode=block",
     *         "clientsubVersion": "aab218782e96b2db855302d353add540",
     *         "Content-Language": "en-US",
     *         "Content-Type": "application/json;charset=UTF-8"
     *     },
     *     "model": {
     *         "data": [
     *             {
     *                 "action": {
     *                     "value": "update"
     *                 },
     *                 "code": {
     *                     "value": "SUCCESS"
     *                 },
     *                 "details": {
     *                     "Modified_Time": 1722507680000,
     *                     "Modified_By": Object{...},
     *                     "Created_Time": 1721983902000,
     *                     "id": "216214000000448023",
     *                     "Created_By": {
     *                         "id": 216214000000414001,
     *                         "name": "i7caitaobao"
     *                     }
     *                 },
     *                 "duplicateField": "Email",
     *                 "message": {
     *                     "value": "record updated"
     *                 },
     *                 "status": {
     *                     "value": "success"
     *                 }
     *             }
     *         ]
     *     },
     *     "object": {
     *         "$ref": "$.model"
     *     },
     *     "statusCode": 200
     * }
     * @param response
     */
    public Long handleZohoResponse(APIResponse<ActionHandler> response){
        log.info("zoho response={}", JSON.toJSONString(response));
        Long id = null;
        if (response != null) {
            if (response.isExpected()) {
                ActionHandler actionHandler = response.getObject();
                if (actionHandler instanceof ActionWrapper) {
                    ActionWrapper actionWrapper = (ActionWrapper) actionHandler;
                    List<ActionResponse> actionResponses = actionWrapper.getData();
                    for (ActionResponse actionResponse : actionResponses) {
                        if (actionResponse instanceof SuccessResponse) {
                            SuccessResponse successResponse = (SuccessResponse) actionResponse;
                            System.out.println("Status: " + successResponse.getStatus().getValue());
                            System.out.println("Code: " + successResponse.getCode().getValue());
                            System.out.println("Details: ");
                            for (Map.Entry<String, Object> entry : successResponse.getDetails().entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue());
                                if(entry.getKey().equals("id")){
                                    id = Long.valueOf(entry.getValue().toString());
                                }
                            }
                            System.out.println("Message: " + successResponse.getMessage().getValue());
                        }
                        else if (actionResponse instanceof APIException) {
                            APIException exception = (APIException) actionResponse;
                            System.out.println("Status: " + exception.getStatus().getValue());
                            System.out.println("Code: " + exception.getCode().getValue());
                            System.out.println("Details: ");
                            for (Map.Entry<String, Object> entry : exception.getDetails().entrySet()) {
                                System.out.println(entry.getKey() + ": " + entry.getValue());
                            }
                            System.out.println("Message: " + exception.getMessage().getValue());
                        }
                    }
                } else if (actionHandler instanceof APIException) {
                    APIException exception = (APIException) actionHandler;
                    System.out.println("Status: " + exception.getStatus().getValue());
                    System.out.println("Code: " + exception.getCode().getValue());
                    System.out.println("Details: ");
                    for (Map.Entry<String, Object> entry : exception.getDetails().entrySet()) {
                        System.out.println(entry.getKey() + ": " + entry.getValue());
                    }
                    System.out.println("Message: " + exception.getMessage().getValue());
                }
            } else {
                Model responseObject = response.getModel();
                Class<? extends Model> clas = responseObject.getClass();
                java.lang.reflect.Field[] fields = clas.getDeclaredFields();
                for (java.lang.reflect.Field field : fields) {
                    field.setAccessible(true);
                    try {
                        log.info(field.getName() + ":" + field.get(responseObject));
                    } catch (IllegalAccessException e) {
                        log.error("反射获取字段失败", e);
                    }
                }
            }
        }
        return id;
    }

    @Getter
    @AllArgsConstructor
    public enum ZohoModule {
        /**
         * Leads, Accounts, Contacts, Deals, Campaigns, Cases, Solutions, Products,
         * Vendors, Price Books, Quotes, Sales Orders, Purchase Orders, Invoices,
         * Appointments, Appointments Rescheduled History, Services and Custom
         */
        LEADS("Leads", "线索"),
        Accounts("Accounts", "客户"),
        CONTACTS("Contacts", "联系人"),
        PRODUCTS("Products", "产品"),
        SALES_ORDERS("Sales_Orders", "销售订单");
        private String moduleName;
        private String desc;
    }
}
