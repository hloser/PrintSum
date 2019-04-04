package com.sun.app.print;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Sun
 * @Date :    2019/4/4
 * @FileName: Print1
 * @Description: 使用volatile关键字交替执行线程
 */
public class Print1 {

    private volatile int num = 0;

    private final int maxNum = 50;

    private ExecutorService service;

    public Print1() {
        //创建一个只有2个线程的线程池
        service = Executors.newScheduledThreadPool(2);
        //线程池中加入线程

    }

    /**
     * 开始执行线程
     */
    public void startPrint() {
        service.submit(new OddNumber());
        service.submit(new EvenNumber());
    }


    /**
     * 打印奇数的线程
     */
    class OddNumber implements Runnable {
        @Override
        public void run() {
            while (num < maxNum) {
                if (num % 2 != 0) {
                    print(num);
                    num++;
                }
            }
        }
    }

    /**
     * 打印偶数的线程
     */
    class EvenNumber implements Runnable {
        @Override
        public void run() {
            while (num < maxNum) {
                if (num % 2 == 0) {
                    print(num);
                    num++;
                }
            }
        }
    }

    private synchronized void print(int i) {
        System.out.println("当前线程：" + Thread.currentThread().getName() + "数字:" + i);
    }

}
