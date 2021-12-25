package com.bigcow.cn.code;

import java.util.concurrent.LinkedBlockingQueue;

public class NonRejectedLinkedBlockingQueue<T> extends LinkedBlockingQueue<T> {

    public NonRejectedLinkedBlockingQueue(int capacity, boolean fair) {
        super(capacity);
    }

    @Override
    public boolean offer(T t) {
        // turn offer() into a blocking calls (unless interrupted)
        try {
            put(t);
            return true;
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
        return false;
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
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
