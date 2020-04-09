package com.company.thread2;

public class Demo5 implements Runnable{

    public static void main(String[] args) {
        Demo5 demo5 = new Demo5();
        Thread thread = new Thread(demo5);
        thread.start();

    }

    @Override
    public void run() {
        System.out.println(
                ""
        );
    }
}
