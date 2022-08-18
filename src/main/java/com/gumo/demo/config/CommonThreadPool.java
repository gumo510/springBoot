package com.gumo.demo.config;

import com.gumo.demo.constants.GlobalConstants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author:LiuWei
 * @Description:
 * @Date: Created in 20:39 2019/4/22
 * @Modified By:
 */
public abstract class CommonThreadPool {
    /**
     * 线程池，io密集型
     * CommonThreadPool.synExecutor.execute(()->{});
     */
    public static final ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,2*Runtime.getRuntime().availableProcessors() + 1,10,TimeUnit.MINUTES, new LinkedBlockingQueue<>(GlobalConstants.threadBlockingQueue));

    public static final ExecutorService executorServiceQueueMaxInteger = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() + 1,2*Runtime.getRuntime().availableProcessors() + 1,10,TimeUnit.MINUTES, new LinkedBlockingQueue<>(Integer.MAX_VALUE));


}
