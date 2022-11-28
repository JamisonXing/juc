package com.jamison.test;


import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Thread t = new Thread( () -> {
            log.debug("running");
        }, "t2");

        t.start();
    }
}