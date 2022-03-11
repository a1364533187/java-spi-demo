package com.bigcow.com.spi.biz;

import java.util.concurrent.CountDownLatch;

public class Code2 {

    public static void main(String[] args) throws InterruptedException {
        int[] arr = new int[10];
        CountDownLatch countDownLatch = new CountDownLatch(10);
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new CountTask(i, arr, countDownLatch));
            t.start();
        }
        countDownLatch.await();
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        System.out.println(sum);
    }

    static class CountTask implements Runnable {

        private int i;
        private int[] arr;
        private CountDownLatch countDownLatch;

        public CountTask(int i, int[] arr, CountDownLatch countDownLatch) {
            this.i = i;
            this.arr = arr;
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            for (int j = 1; j <= 10; j++) {
                arr[i] += i * 10 + j;
            }
            countDownLatch.countDown();
        }
    }

}
