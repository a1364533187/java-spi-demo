package com.bigcow.cn;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class IteratorConsumer<T> {

    private static final int RUN = 0, STOP = 1; //线程池消费时运行状态

    private static final int AWAIT_TERM_IN_HOURS = 24;

    /**
     * 多线程消费Iterator<T>
     * @param iter 消费的数据源
     * @param consumer 消费的函数， 如何消费
     * @param threadNum 线程数
     * @param queueSize 队列长度
     */
    @SuppressWarnings("checkstyle:magicnumber")
    public void consume(Iterator<T> iter, Consumer<T> consumer, Integer threadNum, int queueSize) {
        if (threadNum == null) {
            threadNum = Runtime.getRuntime().availableProcessors();
        }
        NonRejectedLinkedBlockingQueue consumerQueue = new NonRejectedLinkedBlockingQueue(queueSize,
                false);
        ExecutorService executor = new ThreadPoolExecutor(threadNum, threadNum, 1L,
                TimeUnit.MINUTES, consumerQueue);
        AtomicInteger runStatus = new AtomicInteger(RUN);
        while (iter.hasNext()) {
            System.out.println(String.format("Consumer queue size: %s, active thread:%s",
                    consumerQueue.size(), ((ThreadPoolExecutor) executor).getActiveCount()));
            final T next = iter.next();
            if (runStatus.get() == STOP) {
                awaitTerminationAfterShutdown(executor);
                continue;
            }
            CompletableFuture.runAsync(() -> {
                consumer.accept(next);
            }, executor).exceptionally(throwable -> {
                System.out.println(String.format("Consumer failed, %s", throwable));
                return null;
            });
        }
        //线程池关闭
        awaitTerminationAfterShutdown(executor);
    }

    public void awaitTerminationAfterShutdown(ExecutorService executorService) {
        if (executorService == null || executorService.isShutdown()) {
            return;
        }
        try {
            executorService.shutdown(); // 非阻塞的
            System.out.println("hahaha");
            if (!executorService.awaitTermination(AWAIT_TERM_IN_HOURS, TimeUnit.HOURS)) { //阻塞等待
                List<Runnable> runnableList = executorService.shutdownNow();
                System.out.println("runnable list: " + runnableList.size());
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 300; i++) {
            list.add(i);
        }
        Iterator<Integer> iter = list.iterator();
        Consumer<Integer> consumer = o -> {
            System.out.println("hello: " + o);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        long start = System.currentTimeMillis();
        IteratorConsumer<Integer> iteratorConsumer = new IteratorConsumer<>();
        iteratorConsumer.consume(iter, consumer, 10, 100);
        long cost = System.currentTimeMillis() - start;
        System.out.println("--->cost ms: " + cost);
    }
}
