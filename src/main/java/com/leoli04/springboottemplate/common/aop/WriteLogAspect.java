package com.leoli04.springboottemplate.common.aop;

import com.alibaba.fastjson.JSON;
import com.leoli04.springboottemplate.common.consts.RequestConst;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.lang.reflect.Method;

/**
 * @Description: 请求应答日志打印处理器
 * @Author: LeoLi04
 * @CreateDate: 2024/7/22 7:23
 * @Version: 1.0
 */
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WriteLogAspect {
    private static Logger logger = LoggerFactory.getLogger(WriteLogAspect.class);
    private static String lineSplit = "\n|\t";
    public static final String CONTROLLER_PL = "controller_log";

    @Pointcut("@annotation(com.leoli04.springboottemplate.common.aop.WriteLog)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String currentUserId = "";
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            if (request != null) {
                currentUserId = request.getHeader(RequestConst.HEADER_USER_ID);
            }
        }
        long startTime = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        printStart(stringBuilder);
        stringBuilder.append(currentUserId);
        stringBuilder.append(";");
        buildMethodInfo(joinPoint, stringBuilder);
        stringBuilder.append(lineSplit);

        try {
            Object retVal = joinPoint.proceed();
            String json = JSON.toJSONString(retVal);
            // 连续11个数字 直接替换
            if (StringUtils.isNotEmpty(json)) {
                json = json.replaceAll("\"\\d{7}(\\d{4})\"", "\"*******$1\"").replaceAll("=\\d{7}(\\d{4})", "=*******$1");
            }
            stringBuilder.append("返回值:").append(json);
            return retVal;
        } catch (Throwable ex) {
            stringBuilder.append("发生异常:");
            throw ex;
        } finally {
            stringBuilder.append(lineSplit);
            stringBuilder.append("耗时(毫秒):");
            stringBuilder.append(System.currentTimeMillis() - startTime);
            printEnd(stringBuilder);
            logger.info(stringBuilder.toString());
        }
    }

    private void buildMethodInfo(JoinPoint joinPoint, StringBuilder stringBuilder) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        stringBuilder.append(method.getName());
        stringBuilder.append(lineSplit);
        stringBuilder.append("参数依次为:");
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            stringBuilder.append(lineSplit);
            stringBuilder.append("\t\t");
            stringBuilder.append(i);
            stringBuilder.append(":");
            if (joinPoint.getArgs()[i] instanceof ServletRequest || joinPoint.getArgs()[i] instanceof ServletResponse || joinPoint.getArgs()[i] instanceof MultipartFile || joinPoint.getArgs()[i] instanceof StandardMultipartHttpServletRequest) {
                //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                continue;
            }
            String json = JSON.toJSONString(joinPoint.getArgs()[i]);
            if (StringUtils.isNotEmpty(json)) {
                json = json.replaceAll("\"\\d{7}(\\d{4})\"", "\"*******$1\"").replaceAll("=\\d{7}(\\d{4})", "=*******$1");
            }
            stringBuilder.append(json);
        }
    }
    private void printStart(StringBuilder stringBuilder) {
        stringBuilder.append("\n+-" + CONTROLLER_PL + "-");
    }

    private void printEnd(StringBuilder stringBuilder) {
        stringBuilder.append("\n\\__________________");
    }
}
