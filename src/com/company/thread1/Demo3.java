package com.company.thread1;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Demo3 {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

/**
 * 操作数组:
 * 可以获取数组的在内容中的基本偏移量（arrayBaseOffset），获取数组内元素的间隔（比例），
 * 根据数组对象和偏移量获取元素值（getObject），设置数组元素值（putObject），示例如下。
 */
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        int u = unsafe.addressSize();
        System.out.println(u);

        String[] strings = new String[]{"1", "2", "3"};
        long i = unsafe.arrayBaseOffset(String[].class);//从16开始 存储第一个元素
        System.out.println("string[] base offset is :" + i);

//every index scale
        long scale = unsafe.arrayIndexScale(String[].class);//每次间隔4偏移量
        System.out.println("string[] index scale is " + scale);//范围
       // unsafe.putObject(strings, i + scale * 0, "100");
//print first string in strings[]
        System.out.println("first element is :" + unsafe.getObject(strings, 20l));

//set 100 to first string
        unsafe.putObject(strings, i + scale * 0, "100");

//print first string in strings[] again
        System.out.println("after set ,first element is :" + unsafe.getObject(strings, i + scale * 0));

    }
}
