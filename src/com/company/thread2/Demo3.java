package com.company.thread2;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class Demo3 implements Runnable {


    //wait 和notify 必须跟同步块
    public static void main(String[] args) throws InterruptedException {
        Demo3 demo3 = new Demo3();
        Thread thread = new Thread(demo3);
        thread.start();
        while (true) {
            synchronized (demo3) {
                System.out.println("主线程执行");
                demo3.notify();//等待
                Thread.sleep(1000);
            }
        }
    }
    @Override
    public synchronized void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("自有线程执行");
            try {
                this.wait();//等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

