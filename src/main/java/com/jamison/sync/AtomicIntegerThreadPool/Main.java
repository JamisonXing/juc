package com.jamison.sync.AtomicIntegerThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jamison
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger m = new AtomicInteger(0);
        //创建工厂
        MyThreadFactory factory = new MyThreadFactory();
        //线程数量
        int num = 10;
        CountDownLatch latch = new CountDownLatch(num);
        //创建实例对象
        Task task = new Task(m, latch);
        Thread thread;

        //测试：创建10个线程并启动
        for (int i = 0; i < num; i++) {
            thread = factory.newThread(task);
            thread.start();
        }
        latch.await();
        System.out.println(m);
    }
}
