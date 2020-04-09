package com.company.thread2;

public class Target implements Runnable {

    @Override
    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + " 线程的 优先级");
        }
    }
}
