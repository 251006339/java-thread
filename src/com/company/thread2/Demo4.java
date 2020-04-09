package com.company.thread2;

public class Demo4 extends Thread{
//ThreadGoup
  //指定线程名称
    public Demo4(String name) {
        super(name);
    }

    public static void main(String[] args) {
        Demo4 demo4 = new Demo4("thread-1");
    //    demo4.setDaemon(true);//守护线程
        demo4.start();
        demo4.interrupt();//中断设置给false--ture
        System.out.println("主线程执行完,会使其他的线程结束");
    }

    @Override
    public void run() {
        System.out.println("---");
        //是否中断,如果是中断就结束
        while(!interrupted()){

            System.out.println("thread-1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
