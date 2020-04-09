package com.company.thread;

public class ThreadDemo4 {

    public static void main(String[] args) {
        Hello hello = new Hello();

        for (int i = 0; i < 10200; i++) {
            Thread thread = new Thread(hello);
            thread.start();
        }
        for (int i = 0; i < 10200; i++) {
            Thread thread = new Thread(hello);
            thread.start();
            thread.interrupt();
        }

                //        for (int i = 0; i < 1000; i++) {
//            Thread thread = new Thread(hello);
//            thread.start();
//        }
    }
}

