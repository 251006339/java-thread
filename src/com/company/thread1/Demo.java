package com.company.thread1;

public class Demo {

    public static void main(String[] args) throws Exception {
        MyThreadPoolExecutor2 myThreadPoolExecutor2 = new MyThreadPoolExecutor2(1,1,1);
        myThreadPoolExecutor2.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("=-==");
            }
        });
        myThreadPoolExecutor2.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("=-==");
            }
        });
        myThreadPoolExecutor2.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("=-==");
            }
        });
    }



}
