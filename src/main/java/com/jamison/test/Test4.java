package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author jamison
 * 能不能不start直接run
 */
@Slf4j(topic = "c.Test4")
public class Test4 {
    public static void main(String[] args) {
        Thread t1 = new Thread("t1"){
            @Override
            public void run() {
                log.debug("running");
            }
        };
        //可以执行,只不过是main线程执行
        t1.run();
    }
}
