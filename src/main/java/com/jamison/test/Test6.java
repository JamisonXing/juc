package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 * 查看线程状态
 */
@Slf4j(topic = "c.Test5")
public class Test6 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        t1.start();

        log.debug("t1状态{}", t1.getState());

        try {
            Thread.sleep(500);
        }catch (InterruptedException e) {
            log.debug(String.valueOf(e));
        }

        log.debug("t1状态{}", t1.getState());

    }
}
