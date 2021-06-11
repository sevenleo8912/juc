package com.mashibing.juc.c_006;

/**
 * À¿À¯Œ Ã‚
 */
public class TestDeadLock implements Runnable {
    private int flag = 1;
    private static final Object O1 = new Object();
    private static final Object O2 = new Object();

    @Override
    public synchronized void run() {
        System.out.println("flag = " + flag);
        if (flag == 1) {
            synchronized (O1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (O2) {
                    System.out.println("1");
                }
            }
        }

        if (flag == 0) {
            synchronized (O2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (O1) {
                    System.out.println("0");
                }
            }
        }
    }

    public static void main(String[] args) {
        TestDeadLock td1 = new TestDeadLock();
        td1.flag = 1;
        Thread t1 = new Thread(td1);
        t1.start();
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        TestDeadLock td2 = new TestDeadLock();
        td2.flag = 0;
        Thread t2 = new Thread(td2);
        t2.start();
    }

}
