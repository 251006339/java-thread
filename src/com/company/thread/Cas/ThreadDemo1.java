package com.company.thread.Cas;

import sun.misc.Unsafe;

import java.util.concurrent.locks.ReentrantLock;

public class ThreadDemo1 {


    public static void main(String[] args) {

        Hello hello = new Hello();
        Thread thread = new Thread(hello);
        thread.start();
        Thread thread1 = new Thread(hello);
        thread1.start();


    }
}
