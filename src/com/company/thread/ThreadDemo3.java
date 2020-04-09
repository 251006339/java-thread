package com.company.thread;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class ThreadDemo3 {
    static  volatile int status = 0;

    static void lock() {
        while (! compareAntSwan(0, 1)) {
            try {
                System.out.println("自选");
                Thread.sleep(10);//睡眠10秒,
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
  //返回旧值,
    private static int compareAntSet(int expire, int update) {
        int oldValue = status;
        if (oldValue == expire) {
            status = update;
        }
        return oldValue;
    }

    private static boolean compareAntSwan(int expire, int update) {
      //初始值和原始值进行比较,如果不一致,
        return expire==compareAntSet(expire,update);
    }

    static void unlock() {
      status=0;
    }

    public static void main(String[] args) {
        for (int i = 0; i <5 ; i++) {
            Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock();
                System.out.println("-");
                unlock();
            }
        }
        );
        thread.start();

        }
    }


}
