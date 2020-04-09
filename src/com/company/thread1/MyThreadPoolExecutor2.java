package com.company.thread1;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.security.KeyStore;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPoolExecutor2 {
    int maxPoolSize;
    int corePoolSize;
    int workQueueSize;
    LinkedBlockingQueue<Runnable> workQueue;//任务仓库
    AtomicInteger currentPoolSize = new AtomicInteger(0);

    public MyThreadPoolExecutor2(int maxPoolSize, int corePoolSize, int workQueueSize) {
        this.maxPoolSize = maxPoolSize;
        this.corePoolSize = corePoolSize;
        this.workQueueSize = workQueueSize;
        this.workQueue = new LinkedBlockingQueue<Runnable>(workQueueSize);
    }
    //提交任务
    public void execute(Runnable task) throws Exception {
        //1.任务的数量过来后进行比较小于就执行
        if (currentPoolSize.get() < corePoolSize) {
            if (currentPoolSize.incrementAndGet() <= corePoolSize) {
                new Worker(task).start();
            }else{//减一
                currentPoolSize.decrementAndGet();
            }
        }

        //队列加满会返回false
        if(workQueue.offer(task)){
            return;//返回结束
        }
        //再进入的线程和最大的线程对比
        if(currentPoolSize.get()<maxPoolSize){
            //1++;
            if(currentPoolSize.incrementAndGet()<=maxPoolSize){
                new Worker(task).start();
               return;
            }else{
                //减一
                currentPoolSize.decrementAndGet();
            }
        }
        //4 拒绝策略
        throw new Exception("拒绝策略");


    }
    public class Worker extends Thread {
        Runnable task;
        public Worker(Runnable task) {
            this.task = task;
        }
        //执行这个方法
        @Override
        public void run() {
            System.out.println("------");
            try {
                while (true) {
                    //当前的任务不为空,或者队列出列的任务不为空 队列堵塞中
                    if (task != null || (task = workQueue.take()) != null) {
                        //task--就执行
                        task.run();
                        task = null;
                    }else{//为空就退出
                        break;
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
