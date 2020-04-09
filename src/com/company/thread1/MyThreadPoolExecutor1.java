package com.company.thread1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadPoolExecutor1 {
    int corePoolSize;//核心线程数
    int maxPoolSize;//最大线程数
    int workQueueSize;//队列的大小数
    LinkedBlockingQueue<Runnable> workBlocking;
    AtomicInteger currentPoolSize = new AtomicInteger(0);//当前线程数的大小

    public MyThreadPoolExecutor1(int corePoolSize, int maxPoolSize, int workQueueSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueueSize = workQueueSize;
    }

    //2execute提交任务
    public void execute(Runnable task) {
        if (currentPoolSize.get() < corePoolSize) {
            if (currentPoolSize.incrementAndGet() <= corePoolSize) {
                //执行任务0
            }
        }
        //2加入任务
        if(workBlocking.offer(task)){
             return;
        }
        //3当前线程数小于最大的线程数
        if(currentPoolSize.get()<maxPoolSize){
          if(currentPoolSize.incrementAndGet()<=maxPoolSize){
              new Worker(task).start();
              return;
          }else{
              //当前线程减一
              currentPoolSize.decrementAndGet();//当前线程减去1
          }
        }



    }


    public class Worker extends Thread {
        Runnable firstWork;

        public Worker(Runnable firstWork) {
            this.firstWork = firstWork;
        }

        @Override
        public void run() {
            System.out.println(
                    "执行任务");
            Runnable task = firstWork;
            try {
                while (true) {
                    if ((task != null || (task = workBlocking.take()) != null)) {
                        break;//退出
                    }
                    task.run();
                    task = null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
//3