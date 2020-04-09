package com.company.thread;

public class ThreadDemo2 {
    volatile int status=0;
    void lock(){
        while(!compartAndSwan(0,1)){
            //切换cpu
            Thread.yield();
        }
    }
    private int comparAndSet(int expert, int update) {
          int oldValue=status;
          if(oldValue==expert){
              status=update;
          }
          return  oldValue;
    }
    private boolean compartAndSwan(int expert, int update) {
      //修改值和原始值一致说明抢到锁;
     //修改值和原始值不一致说明没有抢到锁;
      return  expert==comparAndSet(expert,update);
    }
    void unlock(){
        status=0;
    }
}
