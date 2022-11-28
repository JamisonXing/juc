package com.jamison.sync.AtomicIntegerThreadPool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jamison
 */
public class Task implements Runnable{
    AtomicInteger m;
    CountDownLatch latch;
    public Task(AtomicInteger m, CountDownLatch latch) {
        this.m = m;
        this.latch = latch;
    }

    private static final int SUM = 10000;
    @Override
    public void run() {
        for (int i = 0; i < SUM; i++) {
            m.incrementAndGet();
        }
        latch.countDown();
    }

}
