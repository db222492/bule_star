package com.xinzeyijia.houselocks.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 该线程池比较适合没有固定大小并且比较快速就能完成的小任务，
 * 它将为每个任务创建一个线程。那这样子它与直接创建线程对象（new Thread()）有什么区别呢？
 * 好处就在于60秒内能够重用已创建的线程。
 * CachedThreadPool：无界线程池，可以进行自动线程回收。
 */

public class CachedThreadPool {

    private ExecutorService pool;

    private static class SingPool {
        private static final CachedThreadPool CACHED_THREAD_POOL = new CachedThreadPool();
    }

    public static CachedThreadPool getInstance() {
        return SingPool.CACHED_THREAD_POOL;
    }

    /*初始化线程池*/
    public CachedThreadPool init() {
        pool = Executors.newSingleThreadExecutor();
        return this;
    }

    /*提交任务执行*/
    public void execute(Runnable r) {
        init();
        pool.execute(r);
    }

    /* 关闭线程池*/
    public void unInit() {
        if (pool == null || pool.isShutdown()) return;
        pool.shutdownNow();
        pool = null;
    }
}
 