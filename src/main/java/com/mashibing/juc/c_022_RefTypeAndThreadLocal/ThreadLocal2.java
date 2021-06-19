/**
 * ThreadLocal线程局部变量
 * <p>
 * ThreadLocal是使用空间换时间，synchronized是使用时间换空间
 * 比如在hibernate中session就存在与ThreadLocal中，避免synchronized的使用
 * <p>
 * 运行下面的程序，理解ThreadLocal
 *
 * @author 马士兵
 */
package com.mashibing.juc.c_022_RefTypeAndThreadLocal;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class ThreadLocal2 {
    //volatile static Person p = new Person();
//    static ThreadLocal<Person> tl = new ThreadLocal<>();
//    private static final ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMdd"));
    static ThreadLocal<Person> tl1 = ThreadLocal.withInitial(Person::new);

    public static void main(String[] args) {
        ThreadLocal2 t = new ThreadLocal2();
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(t.method1().name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Person person = new Person();
            person.name="李四";
            tl1.set(person);
            System.out.println(t.method1().name);
        }).start();
    }

    public Person method1() {
        Person person = tl1.get();
        return person;
    }

    static class Person {
        String name = "zhangsan";
    }
}


