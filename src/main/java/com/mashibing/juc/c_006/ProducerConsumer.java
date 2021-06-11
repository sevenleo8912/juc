package com.mashibing.juc.c_006;

public class ProducerConsumer {
    public static void main(String[] args) {
        SyncStack ss = new SyncStack();
        Producer p = new Producer(ss);
        Consumer c = new Consumer(ss);
        new Thread(p).start();
        new Thread(c).start();
    }

}

class WoTou {
    private int id;

    public WoTou(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "WoTou{" +
                "id=" + id +
                '}';
    }
}

class SyncStack {
    int index = 0;
    WoTou[] arrWt = new WoTou[6];

    public synchronized void push(WoTou woTou) {
        if (index == arrWt.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        arrWt[index] = woTou;
        index++;
    }

    public synchronized WoTou pop() {
        if (index == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.notify();
        index--;
        return arrWt[index];
    }
}

class Producer implements Runnable {
    private SyncStack ss;

    public Producer(SyncStack ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            WoTou wt = new WoTou(i);
            ss.push(wt);
            System.out.println("生产了： " + wt);
            try {
                Thread.sleep((long) (Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    SyncStack ss;

    public Consumer(SyncStack ss) {
        this.ss = ss;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            WoTou wt = ss.pop();
            System.out.println("消费了：" + wt);
            try {
                Thread.sleep((long) (Math.random()*1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
