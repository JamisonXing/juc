package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.TestState")
public class TestState {
    public static void main(String[] args) throws InterruptedException {
        //new
        Thread t1 = new Thread(() -> {

        });

        //runnable
        Thread t2 = new Thread(() -> {
            while(true) {

            }
        });
        t2.start();

        //terminated
        Thread t3 = new Thread(() -> {
            log.debug("what");
        });
        t3.start();

        //timed_waiting
        Thread t4 = new Thread(() -> {
            synchronized(TestState.class) {
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t4.start();

        //waiting
        Thread t5 = new Thread(() -> {
            try {
                t2.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        t5.start();

        //block
        Thread t6 = new Thread(() -> {
           synchronized (TestState.class) {
               try {
                   TimeUnit.SECONDS.sleep(1);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
           }
        });
        t6.start();


        TimeUnit.SECONDS.sleep(1);
        log.debug("t1 state {}", t1.getState());
        log.debug("t2 state {}", t2.getState());
        log.debug("t3 state {}", t3.getState());
        log.debug("t4 state {}", t4.getState());
        log.debug("t5 state {}", t5.getState());
        log.debug("t6 state {}", t6.getState());
    }
}
