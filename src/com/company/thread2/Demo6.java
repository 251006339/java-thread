package com.company.thread2;

public class Demo6 {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                super.run();
            }

        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("");
            }
        }).start();
    }
}