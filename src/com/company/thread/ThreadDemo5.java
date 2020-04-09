package com.company.thread;

import java.util.concurrent.locks.LockSupport;

public class ThreadDemo5 {


    public static void main(String[] args) {


            Thread thread1 = new Thread("1"){
                @Override
                public void run() {
                    LockSupport.park();
                    System.out.println(Thread.currentThread().getName());
                //    LockSupport.park();
                    System.out.println("----");

                }
        };
        thread1.start();
        try {
            Thread.sleep(3300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("1111111111111");
        LockSupport.unpark(thread1);

    }


}
