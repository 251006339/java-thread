package com.company.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {

    public static void main(String[] args) {

        Callable<Integer> integerCallable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                int i=100;
                System.out.println("------------");
                return i;
            }
        };
        FutureTask futureTask = new FutureTask<Integer>(integerCallable);
        FutureTask futureTask1 = new FutureTask<Integer>(integerCallable);
        Thread thread = new Thread(futureTask);
        Thread thread1 = new Thread(futureTask1);
        thread.start();
        thread1.start();
        try {
            Thread.currentThread().join(1000);
            //线程是否完成
            boolean done = futureTask.isDone();
            if (done) {
                Object o = futureTask.get();//自旋等待
                System.out.println(o);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
