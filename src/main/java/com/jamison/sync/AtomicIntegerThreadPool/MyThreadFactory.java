package com.jamison.sync.AtomicIntegerThreadPool;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author jamison
 */
public class MyThreadFactory {
    /**
     * 复写newThread方法
     */
    public Thread newThread(Runnable r) {
        //创建一个线程
        Thread t = new Thread(r);
        return t;
    }
}
