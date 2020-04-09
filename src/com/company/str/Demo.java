package com.company.str;

public class Demo {

    public static void main(String[] args) {
        String string="123";
        char[] chars = string.toCharArray();
        String s = string + "4";
        char[] chars1 = s.toCharArray();
        System.out.println(chars);
        System.out.println(chars1);
 StringBuffer stringBuffer=new StringBuffer();//  value = new char[capacity] char 16的数组..synchronized
        StringBuffer append = stringBuffer.append(s);//char [] char 16的数组,下次来直接在数组后面加入
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder append1 = stringBuilder.append(s);
        append1.append(s);//没有synchronized 修饰
    }
}
