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
            //���׳���������쳣����Ϊû�л�ȡ����unlock��ʱ����쳣���������ж�һ���Ƿ������ִ�С�
            locks.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String dateStr= "2016��10��25��";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy��MM��dd��");
        LocalDate date= LocalDate.parse(dateStr, formatter);
        System.out.println(date);
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy��MM��dd�� hh:mm a");
        String nowStr = now.format(format);
        System.out.println(nowStr);
//        TestTryLock test =new TestTryLock();
//        Thread t1 = new Thread(test,"�߳�1");
//        Thread t2 = new Thread(test,"�߳�2");
//        t1.start();
//        t2.start();
//        t1.join();
//        System.out.println("over");
    }

}