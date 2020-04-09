package com.company.thread2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Demo9{

        //j console  可以发现死锁    饥饿
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Object> objects = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 30; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"线程的");
                    System.out.println("run");
                }
        });

    }
        synchronized (objects){
            objects.wait(3000);//把当前的线程等待3秒
        }
        System.out.println("--------");

}
}
