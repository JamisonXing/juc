package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

import com.jamison.util.Sleeper;

import static com.jamison.util.Sleeper.sleep;

/**
 * @author jamison
 */
@Slf4j(topic = "c.Test8")
public class Test8 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            log.debug("洗水壶");
            sleep(1);
            log.debug("烧水");
            sleep(5);
        }, "老王");


        Thread t2 = new Thread(() -> {
            log.debug("洗茶壶");
            sleep(1);
            log.debug("洗茶杯");
            sleep(2);
            log.debug("拿茶叶");
            sleep(1);
            try {
                t1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("泡茶");
        }, "小王");

        t1.start();
        t2.start();
    }
}
