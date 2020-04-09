package com.company.thread1;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
////这就是一个原理学习的入口的复用线程,减少创建和销毁,线程执行用户提交的任务大小控制,
//// 核心线程:常驻,不会关的  超出核心线程数量创建的可能会被线程池自己销毁 <零时工>
public class MyThreadPoolExecutor {
    int corePoolSize;//核心线程数
    int maxPoolSize;//最大线程数
    int workQueueSize;//队列大小数
    LinkedBlockingQueue<Runnable> workQueue;//任务仓库

    AtomicInteger currentPoolSize = new AtomicInteger(0);//当前线程池的大小

    public MyThreadPoolExecutor(int corePoolSize, int maxPoolSize, int workQueueSize) {
        this.corePoolSize = corePoolSize;
        this.maxPoolSize = maxPoolSize;
        this.workQueueSize = workQueueSize;
        workQueue = new LinkedBlockingQueue<Runnable>(workQueueSize);
    }

    //2execute提交任务
    public void execute(Runnable task) throws Exception {
        //1创建线程步骤  ,如果核心线程
        if (currentPoolSize.get() < corePoolSize) {//做一次数据判断:
        //线程A执行刚好执行,,线程b过来之后在执行,
            if (currentPoolSize.incrementAndGet() <= corePoolSize) {//线程池的大小,在进行判断
                new Worker(task).start();//线程状态切换
                return;
            } else {//线程B执行减一
                currentPoolSize.decrementAndGet();//减一
            }
        }
        //2任务队列是否已满,没满,则将新提交的任务存储在工作队列里
        if (workQueue.offer(task)) {
            return;
        }
        //3.是否达到线程池最大的数量?没达到,则创建一个新工作线程执行任务
        if (currentPoolSize.get() < maxPoolSize) {//做一个数量判断
            if (currentPoolSize.incrementAndGet() <= maxPoolSize) {
                new Worker(task).start();//线程状态切换
                return;
            } else {//如果发现超过了核心线           程数量的大小限制
                currentPoolSize.decrementAndGet();//减一;
            }
        }
        //4,拒绝处理这个任务
        throw new Exception("拒绝任务");
    }



    public class Worker extends Thread {
       Runnable firstTask;
      public Worker(Runnable firstTask) {
            this.firstTask = firstTask;
        }


        @Override
        public void run() {
            Runnable task = firstTask;
            try {
                while (true) {  //线程在这里阻塞  take();
                    //线程A执行run,线程B过来,加入队列;当线程A没执行完,再执行会获得任务
                    if (!(task != null || (task = workQueue.take()) != null)) break;
                    task.run();
                    task = null;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
//1.线程是什么时候创建(初始化时可以创建,提交任务的时候可以创建)
// 可以复用,不会随便的关掉,(执行用户提交的runnable)
//2.线程池线程主要是干什么事情?执行runnable,复用线程执行用户的runnable
//3.线程池里面的线程在没有任务可执行的时候,做什么事情,(处于什么状态)
//4.提交一个任务,执行逻辑应该是什么样子的?
//5.线程池线程怎么被销毁的
//.根据推理
//队列功能,线程状态,java集合,原子类操作,cas操作,线程安全
//1.是否达到核心线程数量.没达到,创建一个工作线程来执行任务,
//2.工作队列是否已满,则将新提交任务存储在工作队列里,
//.3是否达到线程池大数量,没到达,则创建一个新的工作线程来执行任务>
//.4最后,执行拒绝策略来处理这个任务;

//1.任务是什么?
//.Runnable的run()方法内的代码块
//2.多线程的目的是什么?
//充分利用cpu资源,并发做多件事;