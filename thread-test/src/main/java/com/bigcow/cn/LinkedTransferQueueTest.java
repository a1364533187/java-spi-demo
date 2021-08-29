package com.bigcow.cn;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LinkedTransferQueueTest {

    public static void main(String[] args) {
        ZeroQueueSizeLinkedTransferQueue transferQueue = new ZeroQueueSizeLinkedTransferQueue<Integer>();

        ThreadPoolExecutor executeExecutorService = new ThreadPoolExecutor(10, 10, 60,
                TimeUnit.SECONDS, transferQueue);

        for (int i = 0; i < 100; i++) {
            final int temp = i;
            executeExecutorService.submit(() -> {
                System.out.println("" + temp);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--->" + transferQueue.size());
            });
        }

        executeExecutorService.shutdown();
    }

    static class ZeroQueueSizeLinkedTransferQueue<T> extends LinkedTransferQueue<T> {

        @Override
        public boolean offer(T o) {
//            try {
//                transfer(o);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
            put(o);
            return true;
        }
    }
}
