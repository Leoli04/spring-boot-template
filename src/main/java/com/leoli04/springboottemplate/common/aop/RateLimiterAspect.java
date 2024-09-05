package com.leoli04.springboottemplate.common.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.leoli04.springboottemplate.common.exception.LimitException;
import com.leoli04.springboottemplate.common.util.GuavaRateLimiterUtil;
import com.leoli04.springboottemplate.common.util.IpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @Description: 限流切面
 * @Author: LeoLi04
 * @CreateDate: 2024/8/25 16:30
 * @Version: 1.0
 */
@Aspect
@Component
public class RateLimiterAspect {
    @Autowired
    private HttpServletRequest request;
    @Pointcut("@annotation(com.leoli04.springboottemplate.common.aop.Limiter)")
    public void rateLimit() {
    }

    @Around("rateLimit()")
    public Object pointcut(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = point.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = point.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        Limiter annotation = currentMethod.getAnnotation(Limiter.class);
        //获取注解每秒加入桶中的token
        double limitNum = annotation.LimitNum();
        // 注解所在方法名区分不同的限流策略
        String functionName = msig.getName();
        String ipAddress = IpUtil.getIpAddress(request);
        String key = ipAddress + functionName;
        GuavaRateLimiterUtil rateLimiterUtil = GuavaRateLimiterUtil.getInstance();
        if (!rateLimiterUtil.hasKey(key)) {
            rateLimiterUtil.setPermits(key, limitNum);
            rateLimiterUtil.tryAcquire(key);
            return point.proceed();
        }else {
            if (rateLimiterUtil.tryAcquire(key)) {
                return point.proceed();
            }else{
                throw new LimitException("500","请求繁忙");
            }
        }
    }
}
