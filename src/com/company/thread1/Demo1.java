package com.company.thread1;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Demo1 {
   Demo demo;
    public static void main(String[] args) throws ParseException, NoSuchFieldException, IllegalAccessException, InstantiationException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        long l = TimeUnit.SECONDS.toNanos(new Long(2));
        System.out.println(l);//yyyy-MM-dd HH:mm:ss'
     //   Date parse = new SimpleDateFormat().parse("yyyy-MM-dd HH:mm:ss");
       // System.out.println(parse);
        LockSupport.parkNanos(Thread.currentThread(), TimeUnit.SECONDS.toNanos(new Long(2)));//锁住当前// 线程2秒
      //  System.out.println(parse);

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        int i = unsafe.addressSize();
        //实例化私有的构造函数
        Player player = (Player) unsafe.allocateInstance(Player.class);//分配实例;
        player.setName("jack");
        System.out.println(player.getName());
        System.out.println(i);
        Field field = Player.class.getDeclaredField("name");

        //类中提供的3个本地方法allocateMemory、
        // reallocateMemory、
        // freeMemory分别用于分配内存，扩充内存和释放内存，与C语言中的3个方法对应。
      /*  long l1 = unsafe.allocateMemory(1000);
        System.out.println(l1);
        unsafe.reallocateMemory(1000,2000);
        unsafe.freeMemory(1000);*/
        String name = "jack"; //在内存地址16上
        int age = 19;
        player.setName(name);
        player.setAge(age);
        for (Field field1 : Player.class.getDeclaredFields()) {
            System.out.println(field1.getName() + ":对应的内存偏移地址:"
                    + unsafe.objectFieldOffset(field1));
        }
        //上面的输出为 name:对应的内存偏移地址:16
        //age:对应的内存偏移地址:12
        //修改内存偏移地址为12的值（age）,返回true,说明通过内存偏移地址修改age的值成功
        System.out.println(unsafe.compareAndSwapInt(player, 12, age, age + 1));//指定内存地址,如何修改
        System.out.println(unsafe.compareAndSwapObject(player, 16, name, "xiaohu"));//指定内存地址,如何修改
        System.out.println(unsafe.objectFieldOffset(Player.class.getDeclaredField("age")));//不改变偏移量,在上面加1
        //unsafe.putLongVolatile(player ,13, age + 1);//通过修改偏移量修改地址
        System.out.println("age修改后的值:" + player.getAge());
        System.out.println("age修改后的值:" + player.getName());
       Object objectVolatile = unsafe.getObjectVolatile(player, 16);//通过修改偏移量修改值
        System.out.println(objectVolatile);
        //修改内存偏移地址为12的值，但是修改后不保证立马能被其他的线程看到。
      //  unsafe.putOrderedInt(player, 18, age + 2);//通过修改偏移量修改值

        unsafe.putObjectVolatile(player, 78, "");
        System.out.println("name修改 78 后的值:" + unsafe.getObjectVolatile(player,78));
   //可以根据偏移量获得属性值

        System.out.println(unsafe.getIntVolatile(player, 12));
        //修改内存偏移地址为16的值，volatile修饰，修改能立马对其他线程可见
        //unsafe.putObjectVolatile(player, 18, "tom");
       // System.out.println("name:" + player.getName());

        //System.out.println(unsafe.getObjectVolatile(player, 16));
       // System.out.println(unsafe.getObjectVolatile(player, 18));



    }
    public static class Player{

        private String name;
        private int age;

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private Player(){}

        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
}
