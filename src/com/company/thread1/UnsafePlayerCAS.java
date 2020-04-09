package com.company.thread1;

import com.company.thread.Cas.Unsafe;

import java.lang.reflect.Field;

public class UnsafePlayerCAS {
    public static void main(String[] args) throws Exception {
        //通过反射实例化Unsafe
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        //实例化私有的构造函数
        Player player = new Player();
        String name = "jack";
        int age = 19;
        player.setName(name);
        player.setAge(age);
        for (Field field : Player.class.getDeclaredFields()) {
            System.out.println(field.getName() + ":对应的内存偏移地址:"
                    + unsafe.objectFieldOffset(field));
        }
        //上面的输出为 name:对应的内存偏移地址:16
        //age:对应的内存偏移地址:12
        //修改内存偏移地址为12的值（age）,返回true,说明通过内存偏移地址修改age的值成功
        System.out.println(unsafe.compareAndSwapInt(player, 12, age, age + 1));
        System.out.println("age修改后的值:" + player.getAge());

        //修改内存偏移地址为12的值，但是修改后不保证立马能被其他的线程看到。
        unsafe.putOrderedInt(player, 12, age + 2);
        System.out.println("age修改后的值:" + player.getAge());

        //修改内存偏移地址为16的值，volatile修饰，修改能立马对其他线程可见
        unsafe.putObjectVolatile(player, 16, "tom");
        System.out.println("name:" + player.getName());
        System.out.println(unsafe.getObjectVolatile(player, 16));
    }
}



