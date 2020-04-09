package com.company.thread.Cas;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Demo1 {


    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("-------()--");
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("-------()--");
            }
        });
    }

}
