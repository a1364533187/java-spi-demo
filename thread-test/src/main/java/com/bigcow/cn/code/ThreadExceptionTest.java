package com.bigcow.cn.code;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.kuaishou.dp.one.service.common.model.oneapi.QueryAccessControl;

/**
 * Create by suzhiwu on 2021/7/29
 */
public class ThreadExceptionTest {

    private static final int POOL_SIZE = 10;

    //    public static void main(String[] args) throws InterruptedException {
    //        //无队列长队的消费queue, 当无可用的线程时，任务阻塞
    //        BlockingQueue<Runnable> executeWorkQueue = new LinkedTransferQueue<>();
    //        ThreadPoolExecutor executeExecutorService = new ThreadPoolExecutor(POOL_SIZE, POOL_SIZE, 60,
    //                TimeUnit.SECONDS, executeWorkQueue);
    //        CountDownLatch countDownLatch = new CountDownLatch(POOL_SIZE + 10);
    //        for (int i = 0; i < POOL_SIZE + 10; i++) {
    //            final int tempI = i;
    //            executeExecutorService.submit(() -> {
    ////                try {
    //                    System.out.println("--->" + tempI + "--->ThreadId: " + Thread.currentThread());
    //                    throw new StackOverflowError("error");
    ////                } catch (Throwable t) {
    ////                    System.out.println(t.getMessage());
    ////                }
    ////                countDownLatch.countDown();
    //            });
    //        }
    //        Thread.sleep(10000);
    ////        countDownLatch.await();
    //        System.out.println(executeExecutorService.getCorePoolSize() - executeExecutorService.getActiveCount());
    //    }

    public static void main(String[] args) {
//        QueryAccessControl queryAccessControl = QueryAccessControl.newBuilder().build();
//        NonRejectedLinkedBlockingQueue<Runnable> workQueue = new NonRejectedLinkedBlockingQueue<>(
//                1000, true);
//        for (int i = 1; i <= 100; i++) {
//            workQueue.add(new Task(String.valueOf(i)));
//        }
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, workQueue);
//        executor.submit(new Task("0"));
//        executor.shutdown();
//        System.out.println("workQueue size = " + workQueue.size() + " after shutdown");
        new Thread(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--->haha");
        }).start();
    }

    static class Task implements Runnable {

        String name;

        public Task(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                System.out.println("task " + name + " is running");
            }
            System.out.println("task " + name + " is over");
        }
    }
}
