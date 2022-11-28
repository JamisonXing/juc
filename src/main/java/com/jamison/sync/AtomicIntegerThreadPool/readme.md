# use factory and countDownLatch 创建和控制Thread
难点：

1. latch.countDown()的放置位置

2. 如何传递latch参数

待优化：

1. 使用在工厂类中线程池创建线程，是否可行