package com.mashibing.juc.c_020;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static class Mythread extends Thread {
        @Override
        public void run() {
            System.out.println(getName()+"��ʼִ��");
            System.out.println(getName()+"��ʼִ��");
            System.out.println(getName()+"��ʼִ��");
            System.out.println(getName()+"��ʼִ��");
            System.out.println(getName()+"��ʼִ��");
            LockSupport.park();
            System.out.println(getName()+"���н���");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Mythread t1 = new Mythread();
        t1.start();
        System.out.println("ʹ����park");
        TimeUnit.SECONDS.sleep(2);
        LockSupport.unpark(t1);
        System.out.println("���");
    }
}
