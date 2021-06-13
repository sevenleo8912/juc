package com.mashibing.juc.c_020;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier2 {
    public static void main(String[] args) {
//        CyclicBarrier barrier = new CyclicBarrier(20);

        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满人"));

//        CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("满人，发车");
//            }
//        });

        for(int i=0; i<100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
