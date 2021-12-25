package com.bigcow.cn.code.locksupportdemo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.Consumer;

public class LockSupportTest {

    public static void main(String[] args) {
        //定义线程t1、t2、t3执行的函数方法
        Consumer<String> consumer = str -> {
            while (true) {
                //线程消费许可证，并传入blocker，方便后续排查问题
                LockSupport.park();
                //防止线程是因中断操作唤醒
                if (Thread.currentThread().isInterrupted()) {
                    throw new RuntimeException("线程被中断，异常结束");
                }
                System.out.println(Thread.currentThread().getName() + ":" + str);
            }
        };

        Thread t1 = new Thread(() -> {
            consumer.accept("A");
        });
        Thread t2 = new Thread(() -> {
            consumer.accept("B");
        });
        Thread t3 = new Thread(() -> {
            consumer.accept("C");
        });

        Thread dispatcher = new Thread(() -> {
            int i = 0;
            while (true) {
                if (i % 3 == 0) {
                    LockSupport.unpark(t1);
                } else if (i % 3 == 1) {
                    LockSupport.unpark(t2);
                } else if (i % 3 == 2) {
                    LockSupport.unpark(t3);
                }
                i++;
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
        t2.start();
        t3.start();
        dispatcher.start();
    }

}
