package com.mashibing.juc.c_006;

/**
 * 线程同步后
 */
public class TestSync implements Runnable {
    Timer timer = new Timer();
    public static void main(String[] args) {
        TestSync testSync = new TestSync();
        Thread t1 = new Thread(testSync);
        Thread t2 = new Thread(testSync);
        t1.setName("t1");
        t2.setName("t2");
        t1.start();
        t2.start();
    }

    @Override
    public void run() {
        timer.add(Thread.currentThread().getName());
    }
}

class Timer{
    private static int num = 0;
    public void add (String name) {
//        synchronized (this) {
            num++;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name+"，你是第"+num+"个使用timer的线程");
//        }
    }
}
