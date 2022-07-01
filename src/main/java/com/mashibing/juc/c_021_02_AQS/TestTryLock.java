package com.mashibing.juc.c_021_02_AQS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xiaok
 * @ClassName TestTryLock
 * @date 2021/8/21
 * @description TODO
 */
public class TestTryLock implements Runnable {

    private static Lock locks = new ReentrantLock();
    @Override
    public void run() {
        try {
            if(locks.tryLock(8, TimeUnit.SECONDS)){
                System.out.println(Thread.currentThread().getName()+"-->");
//                Thread.sleep(6000);
                TimeUnit.SECONDS.sleep(6);
            }else{
                System.out.println(Thread.currentThread().getName()+" time out ");
            }
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }finally {
            //会抛出锁对象的异常，因为没有获取锁在unlock的时候出异常，可以先判断一下是否存在在执行。
            locks.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String dateStr= "2016年10月25日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDate date= LocalDate.parse(dateStr, formatter);
        System.out.println(date);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 hh:mm a");
        String nowStr = now.format(format);
        System.out.println(nowStr);
//        TestTryLock test =new TestTryLock();
//        Thread t1 = new Thread(test,"线程1");
//        Thread t2 = new Thread(test,"线程2");
//        t1.start();
//        t2.start();
//        t1.join();
//        System.out.println("over");
    }

}