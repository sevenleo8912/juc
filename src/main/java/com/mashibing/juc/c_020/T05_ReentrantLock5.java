/**
 * reentrantlock�������synchronized
 * ����m1����this,ֻ��m1ִ����ϵ�ʱ��,m2����ִ��
 * �����Ǹ�ϰsynchronized��ԭʼ������
 * <p>
 * ʹ��reentrantlock�������ͬ���Ĺ���
 * ��Ҫע����ǣ�����Ҫ����Ҫ����Ҫ�ֶ��ͷ�������Ҫ������˵���飩
 * ʹ��syn�����Ļ���������쳣��jvm���Զ��ͷ���������lock�����ֶ��ͷ�������˾�����finally�н��������ͷ�
 * <p>
 * ʹ��reentrantlock���Խ��С�����������tryLock�������޷�������������ָ��ʱ�����޷��������߳̿��Ծ����Ƿ�����ȴ�
 * <p>
 * ʹ��ReentrantLock�����Ե���lockInterruptibly���������Զ��߳�interrupt����������Ӧ��
 * ��һ���̵߳ȴ����Ĺ����У����Ա����
 * <p>
 * ReentrantLock������ָ��Ϊ��ƽ��
 *
 * @author mashibing
 */
package com.mashibing.juc.c_020;

import java.util.concurrent.locks.ReentrantLock;

public class T05_ReentrantLock5 extends Thread {
    //����Ϊtrue��ʾΪ��ƽ������Ա�������
    private static ReentrantLock lock = new ReentrantLock(true);

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + "�����");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        T05_ReentrantLock5 rl = new T05_ReentrantLock5();
        Thread th1 = new Thread(rl);
        Thread th2 = new Thread(rl);
        th1.start();
        th2.start();
    }
}
