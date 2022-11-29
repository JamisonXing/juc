package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 * 查看线程状态
 */
@Slf4j(topic = "c.Test5")
public class Test5 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        System.out.println(t1.getState());
        t1.start();
        System.out.println(t1.getState());
        t1.run();
        System.out.println(t1.getState());
    }
}
