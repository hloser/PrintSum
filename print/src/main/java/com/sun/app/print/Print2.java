package com.sun.app.print;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Sun
 * @Date :    2019/4/4
 * @FileName: Print2
 * @Description: 利用synchronized、wait、notifyAll
 */
public class Print2 {

    PrintNum lock = new PrintNum();

    private final int maxNum = 50;

    private ExecutorService service;

    class PrintNum {
        volatile int num = 0;
    }
    public Print2() {
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
            synchronized (lock) {
                while (lock.num < maxNum) {
                    if (lock.num % 2 != 0) {
                        print(lock.num);
                        lock.num++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
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
            synchronized (lock) {
                while (lock.num < 50) {
                    if (lock.num % 2 == 0) {
                        print(lock.num);
                        lock.num++;
                        lock.notifyAll();
                    } else {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }


    private  void print(int i) {
        System.out.println("当前线程：" + Thread.currentThread().getName() + "数字:" + i);
    }





}
