package com.bigcow.cn.code.fiber;

public class JavaThread {

    /**
     * 100w个线程，每个线程处理2千万次运算
     * @param argus
     * @throws InterruptedException
     */
    public static void main(String[] argus) throws InterruptedException {
        long begin = System.currentTimeMillis();
        int threadLength = 1000000;//100w
        Thread[] threads = new Thread[threadLength];
        for (int i = 0; i < threadLength; i++) {
            threads[i] = new Thread(() -> {
                calc();
            });
        }

        for (int i = 0; i < threadLength; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadLength; i++) {
            threads[i].join();
        }
        System.out.println(System.currentTimeMillis() - begin);
    }

    //2kw次计算
    static void calc() {
        int result = 0;
        for (int j = 0; j < 10000; j++) {
            for (int i = 0; i < 200; i++) {
                result += i;
            }
        }
    }
}
