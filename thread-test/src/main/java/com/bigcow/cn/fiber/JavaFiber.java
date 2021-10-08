package com.bigcow.cn.fiber;

import java.util.concurrent.ExecutionException;

import co.paralleluniverse.fibers.Fiber;

public class JavaFiber {

    /**
     * 100w个纤程，每个纤程处理2千万次运算
     * @param argus
     * @throws InterruptedException
     */
    public static void main(String[] argus) throws ExecutionException, InterruptedException {
        long begin = System.currentTimeMillis();
        int fiberLength = 1000000;//100w
        Fiber<Void>[] fibers = new Fiber[fiberLength];
        for (int i = 0; i < fiberLength; i++) {
            fibers[i] = new Fiber(() -> {
                calc();
            });
        }

        for (int i = 0; i < fiberLength; i++) {
            fibers[i].start();
        }
        for (int i = 0; i < fiberLength; i++) {
            fibers[i].join();
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
