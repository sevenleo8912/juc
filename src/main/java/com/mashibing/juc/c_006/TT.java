package com.mashibing.juc.c_006;

public class TT implements Runnable {
    int b = 100;
    public synchronized void m1() {
        b = 1000;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("b = "+b);
    }
    public void m2() {
        System.out.println(b);
    }
	@Override
	public synchronized void run() {
        m1();
	}
	
	public static void main(String[] args) {
        TT tt = new TT();
        Thread t = new Thread(tt);
        t.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        tt.m2();
    }
	
}
