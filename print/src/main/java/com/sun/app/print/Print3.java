package com.sun.app.print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Sun
 * @Date :    2019/4/4
 * @FileName: Print3
 * @Description: ����synchronized ���Զ���������
 */
public class Print3 {

    private int num = 0;

    private final int maxNum = 50;

    private boolean canRuning = true;

    private ExecutorService service;

    public Print3() {
        //����һ��ֻ��2���̵߳��̳߳�
        service = Executors.newScheduledThreadPool(2);
        //�̳߳��м����߳�

    }

    /**
     * ��ʼִ���߳�
     */
    public void startPrint() {
        service.submit(new OddNumber());
        service.submit(new EvenNumber());
    }


    /**
     * ��ӡ�������߳�
     */
    class OddNumber implements Runnable {
        @Override
        public void run() {
            while (num < maxNum) {
                while (true) {
                    if (!canRuning) {
                        continue;
                    }
                    print(num);
                    canRuning = false;
                    num++;
                    break;
                }
            }
        }
    }

    /**
     * ��ӡż�����߳�
     */
    class EvenNumber implements Runnable {
        @Override
        public void run() {
            while (num < maxNum) {
                while (true) {
                    if (canRuning) {
                        continue;
                    }
                    print(num);
                    canRuning = false;
                    num++;
                    break;
                }
            }
        }
    }


    private synchronized void print(int i) {
        System.out.println("��ǰ�̣߳�" + Thread.currentThread().getName() + "����:" + i);
    }

}
