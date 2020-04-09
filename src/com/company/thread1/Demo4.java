package com.company.thread1;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Demo4 {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        int i = unsafe.addressSize();
        int arr[]={5,7,1,3,4,8};
        int i1 = unsafe.arrayBaseOffset(arr.getClass());
        System.out.println("内存的开始位置"+i1);
        int i2 = unsafe.arrayIndexScale(arr.getClass());
        System.out.println(i2);
        //获得第二个位置元素
        int intVolatile = unsafe.getIntVolatile(arr, 20);
        System.out.println(intVolatile);
    }
}
