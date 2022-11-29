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