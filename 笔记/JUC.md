# 课程大纲

![大纲outline](/Users/jamison/Library/Application Support/typora-user-images/image-20221126185852563.png)

![outline2](/Users/jamison/Library/Application Support/typora-user-images/image-20221126190009265.png)

![outline](/Users/jamison/Library/Application Support/typora-user-images/image-20221128134751204.png)

# 1.java线程

## 1.1 创建线程

### 方法1，直接使用Thread

```java
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
```

### 方法2，使用Runnable配合Thread

把线程和任务分开

- Thread代表线程
- Runnable可运行的任务

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
```

### lambda简化

只有含有**@FunctionalInterface**(其实就是接口只有一个抽象方法，看看runnable就知道了)的接口才可以被简化

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Runnable r = () -> {
                log.debug("running");
            };

        Thread t = new Thread(r, "t2");

        t.start();
    }
}
```

或者

```java
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        Thread t = new Thread( () -> {
            log.debug("running");
        }, "t2");

        t.start();
    }
}
```

### 原理之Thread与Runnable

分析Thread源码，理清它与Runnalbe的关系。

**小结**

- 方法1是把线程和任务合并在一起，方法2是把线程和任务分开了。
- 用Runnable更加容易与线程池等高级API配合。
- 用Runnable让任务类脱离了Thread的继承体系，更灵活。

### 方法三，FutureTask配合Thread

FutureTask能够接受Callable类型的参数，用来处理有返回结果的情况

```java
@Slf4j(topic = "c.Test3")
public class Test3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            log.debug("running....");
            Thread.sleep(1000);
            return 100;
        });
        
        Thread t1 = new Thread(task, "t1");
        t1.start();
        
        log.debug("{}", task.get());
    }
}
```



## 1.2 查看运行线程的方法

一些命令：ps -ef, top, jps等

java图形化查看命令jconsole



## 1.3* 原理之线程运行

### 栈与栈帧

Java Virtual Machine Stacks (Java虚拟机栈)

jvm中由堆、栈、方法区所组成，其中栈内存是分给线程用的。

- 每个栈由多个栈帧(Frame)组成,对应着每次方法调用时候所占用的内存
- 每个线程只能有一个**活动**栈帧，对应着当前正在执行哪个方法

栈帧图解：https://www.bilibili.com/video/BV16J411h7Rd?t=1.8&p=21

![栈帧图解](/Users/jamison/Library/Application Support/typora-user-images/image-20221128202452907.png)

### 线程上下文切换

因为一下一些原因导致cpu不再执行当前线程，转而执行另一个线程的代码:

- 线程的cpu时间片用完
- 垃圾回收
- 有更高优先级的线程需要运行
- 线程自己调用了sleep, yield, wait, join, park, synchronized, lock等方法

当Context Switch发生时， 需要由操作系统保存当前线程的状态，并恢复另一个线程的状态：

- 状态包括程序计数器(Progrom Counter Register)，虚拟机栈中每个栈帧的信息，如局部变量，操作数栈，返回地址等
- Context Switch频繁发生会影响性能

![上下文切换](/Users/jamison/Library/Application Support/typora-user-images/image-20221128210511752.png)

## 1.4 常见方法

![常见方法](/Users/jamison/Library/Application Support/typora-user-images/image-20221129193631674.png)

![常用方法](/Users/jamison/Library/Application Support/typora-user-images/image-20221129194248890.png)



## 1.5 主线程和守护线程

默认情况下，java进程需要等其他线程都运行结束，才会结束。有一种特殊的线程叫做守护线程，只要其他非守护线程运行结束结束了，即使守护线程的代码没有执行完，也会被强制结束。

```java
Thread thread;
//讲普通线程设置为守护线程
thread.setDaemon(true);
```



## 1.6 线程状态

### 五种状态

这是从**操作系统**的层面来描述的：

![五种状态](/Users/jamison/Library/Application Support/typora-user-images/image-20221206115421313.png)

### 六种状态

这是从**Java API**层面来描述的：

根据Thread.State枚举，分为六种状态

![6种状态](/Users/jamison/Library/Application Support/typora-user-images/image-20221206115731395.png)

使线程出现六种状态，实例如下：

```java
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

/**
result:
11:51:35:0322 [Thread-2] c.TestState - what
11:51:36:0326 [main] c.TestState - t1 state NEW
11:51:36:0327 [main] c.TestState - t2 state RUNNABLE
11:51:36:0327 [main] c.TestState - t3 state TERMINATED
11:51:36:0327 [main] c.TestState - t4 state TIMED_WAITING
11:51:36:0327 [main] c.TestState - t5 state WAITING
11:51:36:0327 [main] c.TestState - t6 state BLOCKED
*/
```





# 应用篇

## 效率

<center>案例-防止cpu占用到100%</center>

### sleep实现

在没有利用CPU来计算时，不要让while(true)空转浪费cpu，这时可以使用yield或sleep来让出cpu的使用权。

```java
public class Demo1 {
    public static void main(String[] args) {
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(5);
                } catch(Exception e) {}
            }
        });
    }
}
```



- 可以用wait或者条件变量达到类似的效果
- 不同的是，后两种都需要加速，并且需要相应的唤醒操作，一般使用于要进行同步的场景。
- sleep适用于无序锁的场景。







# 并发编程设计模式

## 两阶段终止模式

在T1线程中如何优雅地终止T2？这里的优雅指的是给T2一个料理后事的机会。

### 1. 错误思路

![错误思路](/Users/jamison/Library/Application Support/typora-user-images/image-20221204180816022.png)

### 2. 两阶段终止模式

一个监控案例

![监控案例](/Users/jamison/Library/Application Support/typora-user-images/image-20221204180915338.png)

```java
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
```

