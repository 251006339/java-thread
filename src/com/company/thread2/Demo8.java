package com.company.thread2;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.Timer;
import java.util.TimerTask;

public class Demo8 {

  //query
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timertask is run 一秒执行一个任务 ");
            }
        },0,1000);


    }
}
