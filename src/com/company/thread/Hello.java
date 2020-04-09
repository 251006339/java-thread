package com.company.thread;

import com.sun.org.apache.xpath.internal.res.XPATHErrorResources_ko;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;

public class Hello implements Runnable {
    volatile  int statue=0;
    Thread thread;
    Queue queue= new LinkedBlockingDeque<Thread>();
    int i=0;
    int j=0;

    void lock(){

    while(!compareAndSwan(0,1)){
            park();
        }
    }
    private void park() {
        //放当前线程
        queue.add(Thread.currentThread());
        releaseCpu();//释放cpu资源;
    }

    private void releaseCpu() {
        LockSupport.park();//线程堵塞 由unlock线程释放NG
    }

    void unlock(){
        statue=0;
          //将当前线程加入到等待队列
        Thread t = (Thread) queue.poll();//弹出元素
        unpark(t);
    }

    private void unpark(Thread t) {
        LockSupport.unpark(t);//唤醒当前线程
    }

    int compareAndSet(int expire,int update){
          int oldValue=statue;
        if(oldValue==expire){
            statue=update;
        }
        return oldValue;
    }
    boolean compareAndSwan(int expire,int update){
        return expire==compareAndSet(expire,update);
    }

    @Override
    public void run() {
        lock();//抢到锁执行
        System.out.println(Thread.currentThread().getName());
        System.out.println(i++);
        System.out.println(j++);
        unlock();//释放锁;
    }

}
