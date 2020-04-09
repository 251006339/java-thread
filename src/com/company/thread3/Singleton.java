package com.company.thread3;

public class Singleton {

    private  static volatile Singleton singleton;

    public  static Singleton getSingleton() {
        if(singleton==null) {
            synchronized (singleton) {
                if (singleton == null) {
                    //加votalile是因为创建对象的时候 状态分为几部分了,线程A创建半个,就被线程B拿走,就出现不一致的情况
                    singleton = new Singleton();//指令重排序
                    //申请一块内存空间
                    //在这块空间里实例化对象
                    //instance的引用指向这块空间地址;
                }
            }
        }
        return singleton;
    }
}
