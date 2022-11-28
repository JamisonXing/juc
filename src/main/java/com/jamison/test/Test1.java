package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Text1")
public class Test1 {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };
        t.setName("t1");
        t.start();
        log.debug("running");
    }
}
