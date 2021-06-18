/**
 * �����������⣺���Ա�����
 * ʵ��һ���������ṩ����������add��size
 * д�����̣߳��߳�1���10��Ԫ�ص������У��߳�2ʵ�ּ��Ԫ�صĸ�������������5��ʱ���߳�2������ʾ������
 * <p>
 * ��lists���volatile֮��t2�ܹ��ӵ�֪ͨ�����ǣ�t2�̵߳���ѭ�����˷�cpu�����������ѭ��������ô���أ�
 * <p>
 * ����ʹ��wait��notify������wait���ͷ�������notify�����ͷ���
 * ��Ҫע����ǣ��������ַ���������Ҫ��֤t2��ִ�У�Ҳ����������t2�����ſ���
 * <p>
 * �Ķ�����ĳ��򣬲�����������
 * ���Զ���������������size=5ʱt2�˳�������t1����ʱt2�Ž��յ�֪ͨ���˳�
 * ��������Ϊʲô��
 * <p>
 * notify֮��t1�����ͷ�����t2�˳���Ҳ����notify��֪ͨt1����ִ��
 * ����ͨ�Ź��̱ȽϷ���
 *
 * @author mashibing
 */
package com.mashibing.juc.c_020_01_Interview;

import java.util.ArrayList;
import java.util.List;

public class T04_NotifyFreeLock_copy {
    volatile List list = new ArrayList();

    // ����������һ����ӷ�����һ��ͳ�Ƹ����ķ���
    private void add(Object o) {
        list.add(o);
    }

    private int count() {
        return list.size();
    }

    public static void main(String[] args) {
        T04_NotifyFreeLock_copy nf = new T04_NotifyFreeLock_copy();
        Object lock = new Object();
        // ���������̣߳�һ���߳���ӣ�һ���̼߳��
        Thread t1 = new Thread(() -> {
            // ��ȡ�����ж�����������治��5���Ļ�����ô���õ�ǰ�߳����������ͷ���
            synchronized (lock) {
                System.out.println("t1��ʼ");
                if (nf.count() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t1����");
                // ����������߳�
                lock.notify();
            }
        }, "t1");
        t1.start();
        Thread t2 = new Thread(() -> {
            // ��ȡ��������ѭ�����Ԫ�أ���������������5���Ļ�������������̣߳�
            // ����notify()���������ͷ��������Ի�Ҫ��дһ��wait()���������ͷţ���������̲߳����õ���ǰ������ִ�С�
            synchronized (lock) {
                System.out.println("t2��ʼ");
                for (int i = 0; i < 10; i++) {
                    nf.add(i);
                    System.out.println(Thread.currentThread().getName() + "��add " + i);
                    if (nf.count() == 5) {
                        lock.notify();
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                System.out.println("t2����");
            }
        }, "t2");
        t2.start();
    }

}
