package com.company.thread;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo implements  Runnable{
    static AtomicInteger atomicInteger = new AtomicInteger();
    ReentrantLock reentrantLock = new ReentrantLock();
    public static void main(String[] args) {

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("==--run");
            }
        });
        thread.start();
        Thread thread1  = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println("==--run");
            }
        });
        thread1.start();*/
        ThreadDemo threadDemo = new ThreadDemo();
        for (int i = 0; i <4 ; i++) {
            Thread thread2 = new Thread(threadDemo);
            thread2.start();

        }

        try {
            Thread.currentThread().join(1000);//让其
            Thread.currentThread().suspend();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = atomicInteger.get();
        System.out.println("获得结果   "+i);
    }

    @Override
    public void run() {
//        Thread.currentThread().interrupt();
//
//        Thread.yield();
//        System.out.println(Thread.interrupted());

//        System.out.println(Thread.interrupted());

        reentrantLock.lock();
        reentrantLock.lock();//可重入锁;
        reentrantLock.unlock();
        Condition condition = reentrantLock.newCondition();//ConditionObject
        //condition.await();//   LockSupport.park(this);
        condition.signal();//释放线程
        reentrantLock.lock();//把状态state 1加1 ==2;能调多次的是可重入锁;
        System.out.println(Thread.currentThread().getName());
        int andIncrement = atomicInteger.getAndIncrement();
        reentrantLock.unlock();//把状态state 减1 exclusiveOwnerThread制空;;
        System.out.println("==--run");
    }
}
