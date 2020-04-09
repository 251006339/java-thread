package com.company.thread2;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;

public class Demo10 {

    public static void main(String[] args) {
        Target target = new Target();
        Thread thread1 = new Thread(target);
        Thread thread2 = new Thread(target);
        Thread thread3 = new Thread(target);
        Thread thread4 = new Thread(target);
        thread1.setPriority(1);//优先级
        thread2.setPriority(2);//优先级
        thread3.setPriority(3);//优先级
        thread4.setPriority(4);//优先级
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        //如何避免饥饿问题
        // 设置合理的优先级
        //使用锁来代替synchronized


    }
}
