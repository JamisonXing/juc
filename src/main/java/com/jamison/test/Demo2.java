package com.jamison.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Demo2")
public class Demo2 {
    public static void main(String[] args) throws InterruptedException{
        TwoPhaseTermination t = new TwoPhaseTermination();
        t.start();
        Thread.sleep(4000);
        t.stop();
    }
}

@Slf4j(topic = "c.TwoPhaseTermination")
class TwoPhaseTermination {
    private Thread moniter;
    void start() {
        moniter = new Thread(() -> {
            Thread t1 = Thread.currentThread();
            while(true) {
                if(t1.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控程序");
                }catch (InterruptedException e) {
                    log.debug("重置标记");
                    //重置标记，因为sleep过程中被打断，标记为false,不重置的话，下一次循环不会退出。
                    t1.interrupt();
                    e.printStackTrace();
                }
            }
        });
        moniter.start();
    }

    void stop() {
        moniter.interrupt();
    }
}