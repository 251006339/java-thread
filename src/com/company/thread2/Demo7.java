package com.company.thread2;

import com.company.thread1.Demo1;
import com.sun.xml.internal.ws.resources.UtilMessages;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.LockSupport;

public class Demo7 implements Callable<Integer> {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Demo7 demo7 = new Demo7();
        //执行实现节点的callable的类,
        FutureTask<Integer> integerFutureTask = new FutureTask<Integer>(demo7);//包装以下
        Thread thread = new Thread(integerFutureTask);
        thread.start();
        System.out.println("我先干点别的");
        Integer task = integerFutureTask.get();
        System.out.println("任务返回结果  "+task);

        long nanos=0;
        boolean timed=false;
        long deadline = System.nanoTime();
        Thread.sleep(3000);
        nanos =  System.nanoTime()-deadline;
        LockSupport.parkNanos(Thread.currentThread(), nanos);
        System.out.println("1111");
    }

    @Override
    public Integer call() throws Exception {
        System.out.println("正在进行紧张的计算...");
       Thread.sleep(3000);
        return 1;
    }
}
