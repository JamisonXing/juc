package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author jamison
 * 唤醒sleep方法
 */
@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("enter sleep");
                try {
                    TimeUnit.SECONDS.sleep();
                } catch (InterruptedException e) {
                    log.debug("wake up");
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt t1");
        t1.interrupt();

    }
}
