package com.company.thread.Cas;


import com.company.thread.ReentrantLock1;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

public class  Hello implements  Runnable {

     ReentrantLock1 reentrantLock = new ReentrantLock1();
   int i=0;
    @Override
    public void run() {
        reentrantLock.lockTest();
        i++;
        System.out.println("000");
        reentrantLock.unlock();
    }


    public static void main(String[] args) {
        System.out.println("");
        for1();

    }
    public static void for1(){
        int i=0;
        for (;;){
           i++;
           if(i==20){
               return ;
           }
        }
    }
}
