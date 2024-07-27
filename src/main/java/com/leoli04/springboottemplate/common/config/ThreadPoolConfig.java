package com.leoli04.springboottemplate.common.config;

import com.leoli04.springboottemplate.common.config.property.ThreadPoolProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 线程池配置
 * @Author: LeoLi04
 * @CreateDate: 2024/7/27 16:58
 * @Version: 1.0
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Autowired
    private ThreadPoolProperties poolProperties;
    /**
     * IO密集型任务  = 一般为2*CPU核心数（常出现于线程中：数据库数据交互、文件上传下载、网络数据传输等等）
     * CPU密集型任务 = 一般为CPU核心数+1（常出现于线程中：复杂算法）
     * 混合型任务  = 视机器配置和复杂度自测而定
     */
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    // 声明一个线程池(并指定线程池的名字)
    @Bean("LeoLi04TaskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数 cpuNum：线程池创建时候初始化的线程数
        executor.setCorePoolSize(poolProperties.getCorePoolSize());
        //核心线程数 cpuNum：线程池创建时候初始化的线程数
        executor.setMaxPoolSize(poolProperties.getMaxPoolSize());
        //缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(poolProperties.getQueueCapacity());
        //允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 当任务无法执行时，由提交任务的线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //调度器shutdown被调用时等待当前被调度的任务完成
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时长
        executor.setAwaitTerminationSeconds(60);
        //线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("LeoLi04-");
        executor.initialize();
        return executor;
    }

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        p.setIgnoreUnresolvablePlaceholders(true);
        p.setOrder(1);
        return p;
    }
}
