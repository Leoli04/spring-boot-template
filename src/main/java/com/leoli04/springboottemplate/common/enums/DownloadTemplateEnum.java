package com.leoli04.springboottemplate.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Description: 模版枚举
 * @Author: LeoLi04
 * @CreateDate: 2024/9/28 17:52
 * @Version: 1.0
 */
@AllArgsConstructor
@Getter
public enum DownloadTemplateEnum {
    CATALOG("catalog","导入分类模版.xlsx"),
    ;
    private String templateType;
    private String templateName;

    public static String getTemplateNameByType(String templateType){
        for (DownloadTemplateEnum value : DownloadTemplateEnum.values()) {
            if(value.getTemplateType().equals(templateType)){
                return value.getTemplateName();
            }
        }
        return null;
    }
}
