package com.company.thread;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.concurrent.atomic.AtomicInteger;

public class DemoThread {
    static  volatile int status = 0;
    static int j=0;
  static   void  lock() {
      //后面进程都进这个循环,直到,
        while (!compareAndSwan(0, 1)) {
            System.out.println("自旋");
        }
    }
    static  void unlock(){
        status=0;
  }


  //cas 底层每次只能一个线程进入
    private static synchronized int compareAndSet(int expect, int update) {
        int oldValue = status;
        if (oldValue == expect) {
            status=update;
        }
        return oldValue;
    }
    //A  0  0  1
    //B  1  0
    private static synchronized  boolean compareAndSwan(int expect, int update) {
        //成功没成功

        return expect==compareAndSet(expect,update);
    }

    public static void main(String[] args) {
        for (int i = 0; i <199 ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    lock();
                    System.out.println(j++);
                    unlock();
                }
                
            });
            thread.start();
        }

    }



}
