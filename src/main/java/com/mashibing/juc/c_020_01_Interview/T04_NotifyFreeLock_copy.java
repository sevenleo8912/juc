/**
 * 曾经的面试题：（淘宝？）
 * 实现一个容器，提供两个方法，add，size
 * 写两个线程，线程1添加10个元素到容器中，线程2实现监控元素的个数，当个数到5个时，线程2给出提示并结束
 * <p>
 * 给lists添加volatile之后，t2能够接到通知，但是，t2线程的死循环很浪费cpu，如果不用死循环，该怎么做呢？
 * <p>
 * 这里使用wait和notify做到，wait会释放锁，而notify不会释放锁
 * 需要注意的是，运用这种方法，必须要保证t2先执行，也就是首先让t2监听才可以
 * <p>
 * 阅读下面的程序，并分析输出结果
 * 可以读到输出结果并不是size=5时t2退出，而是t1结束时t2才接收到通知而退出
 * 想想这是为什么？
 * <p>
 * notify之后，t1必须释放锁，t2退出后，也必须notify，通知t1继续执行
 * 整个通信过程比较繁琐
 *
 * @author mashibing
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;

public class T04_NotifyFreeLock_copy {
    volatile List list = new ArrayList();

    // 两个方法，一个添加方法，一个统计个数的方法
    private void add(Object o) {
        list.add(o);
    }

    private int count() {
        return list.size();
    }

    public static void main(String[] args) {
        T04_NotifyFreeLock_copy nf = new T04_NotifyFreeLock_copy();
        Object lock = new Object();
        // 定义两个线程，一个线程添加，一个线程监控
        Thread t1 = new Thread(() -> {
            // 获取锁，判断如果集合里面不是5个的话，那么就让当前线程阻塞并且释放锁
            synchronized (lock) {
                System.out.println("t1开始");
                if (nf.count() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1结束");
                // 唤醒另外的线程
                lock.notify();
            }
        }, "t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            // 获取锁，并且循环添加元素，如果里面的数量是5个的话，唤醒另外的线程，
            // 但是notify()方法不能释放锁，所以还要再写一个wait()方法将锁释放，这样别的线程才能拿到当前锁继续执行。
            synchronized (lock) {
                System.out.println("t2开始");
                for (int i = 0; i < 10; i++) {
                    nf.add(i);
                    System.out.println(Thread.currentThread().getName() + "：add " + i);
                    if (nf.count() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("t2结束");
            }
        }, "t2");
        t2.start();
    }

}
