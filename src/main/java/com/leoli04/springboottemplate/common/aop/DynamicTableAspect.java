package com.leoli04.springboottemplate.common.aop;

import com.alibaba.fastjson.JSON;
import com.leoli04.springboottemplate.common.config.RequestDataHelper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @Description: 动态表名aop
 * @Author: LeoLi04
 * @CreateDate: 2024/9/10 9:35
 * @Version: 1.0
 */
@Aspect
@Component
@Slf4j
public class DynamicTableAspect {

    /**
     * 用于SpEL表达式解析.
     */
    private SpelExpressionParser parser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    private static final String TABLE_NAME = "tableName";
    private static final String TABLE_SUFFIX = "tableSuffix";

    /**
     * 以注解为切点
     */
    @Pointcut("@annotation(com.leoli04.springboottemplate.common.aop.DynamicTable)")
    public void dynamicTable() {
    }

    @Around("dynamicTable()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if(method.isAnnotationPresent(DynamicTable.class)){
            DynamicTable dynamicTable =  (DynamicTable) method.getAnnotation(DynamicTable.class);
            String key = dynamicTable.separateKey();
            // 获取参数
            Object[] args = joinPoint.getArgs();
            if (args.length != 2) {
                log.error("DynamicTableAspect#doAround 缺少参数。args={}", JSON.toJSONString(args));
                return null;
            }
            Expression expression = parser.parseExpression(key);
            // 使用spring的DefaultParameterNameDiscoverer获取方法形参名数组
            String[] paramNames = nameDiscoverer.getParameterNames(method);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for (int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }

            RequestDataHelper.setRequestData(new HashMap<String, Object>() {{
                put(TABLE_NAME, dynamicTable.tableName());
                put(TABLE_SUFFIX, expression.getValue(context));
            }});

            Object proceed = joinPoint.proceed();

            RequestDataHelper.removeRequestData();
            return proceed;
        }else{
            Object proceed = joinPoint.proceed();
            return proceed;
        }
    }

}
