package com.bigcow.cn;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Create by suzhiwu on 2021/7/29
 */
public class ThreadExceptionTest {

    private static final int POOL_SIZE = 10;

    public static void main(String[] args) throws InterruptedException {
        //无队列长队的消费queue, 当无可用的线程时，任务阻塞
        BlockingQueue<Runnable> executeWorkQueue = new LinkedTransferQueue<>();
        ThreadPoolExecutor executeExecutorService = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 60,
                TimeUnit.SECONDS, executeWorkQueue);
        CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE + 10);
        for (int i = 0; i < POOL_SIZE + 10; i++) {
            final int tempI = i;
            executeExecutorService.submit(() -> {
//                try {
                    System.out.println("--->" + tempI + "--->ThreadId: " + Thread.currentThread());
                    throw new StackOverflowError("error");
//                } catch (Throwable t) {
//                    System.out.println(t.getMessage());
//                }
//                countDownLatch.countDown();
            });
        }
        Thread.sleep(10000);
//        countDownLatch.await();
        System.out.println(executeExecutorService.getCorePoolSize() - executeExecutorService.getActiveCount());
    }
}
