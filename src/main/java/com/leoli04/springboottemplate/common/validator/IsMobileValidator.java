package com.leoli04.springboottemplate.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: //todo
 * @Author: LeoLi04
 * @CreateDate: 2024/7/28 0:10
 * @Version: 1.0
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    private boolean required = false;

    /**
     * @Param: {@link  IsMobile } constraintAnnotation
     * @Return: void
     * @TODO: 初始化方法，可以用自定义注解中获取值进行初始化
     **/
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    /**
     * @Param: {@link  String} value
     * @Param: {@link  ConstraintValidatorContext } constraintValidatorContext
     * @Return: {@link boolean}
     *
     **/
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(required){
            return value.length()==11;
        }else {
            if(StringUtils.isEmpty(value)){
                return true;
            }else {
                return value.length()==11;
            }
        }
    }
}
