package com.leoli04.springboottemplate.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 响应类型枚举类
 * @Author: LeoLi04
 * @CreateDate: 2024/7/21 21:53
 * @Version: 1.0
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS("200", "操作成功"),
    SYSTEM_ERROR("10001", "抱歉！系统异常，请稍后再试!"),
    PARAM_ERROR("10002", "参数错误"),
    ILLEGAL_OPERATION_ERROR("10003", "非法操作"),
    NPE_ERROR("10004", "空指针异常"),
    FAIL_UPLOAD_FILE("10020","上传文件失败"),
    ;

    private final String code;

    private final String message;
}
