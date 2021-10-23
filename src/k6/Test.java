package k6;

import java.util.concurrent.CountDownLatch;

/**
 * concurrent包下，常用的3个线程辅助类：CountDownLatch（倒计时闩锁）、CyclicBarrier（循环栅栏）和 Semaphore（信号量）
 *
 * CountDownLatch 是 "倒计时闩锁" 的意思
 * 作用是：让一个线程在等待其它线程执行完后，再执行。
 *
 * 使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。
 * 当计数器的值为0时，表示所有的线程都已经执行完毕，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务。
 *
 * CountDownLatch的用法
 * CountDownLatch典型用法：
 * 1、某一线程在开始运行前等待n个线程执行完毕。
 * 将CountDownLatch的计数器初始化为new CountDownLatch(n)，每当一个任务线程执行完毕，就将计数器减1 countdownLatch.countDown()，
 * 当计数器的值变为0时，在CountDownLatch上await()的线程就会被唤醒。一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 */
public class Test {
    public static void main(String[] args) {
        /**
         * 实现主线程需要等待其它6个分支线程执行结束后，再执行
         * 计数从6开始，表示需要等待的线程数量
         */
        CountDownLatch countDownLatch = new CountDownLatch(6); //开始计数器，让主线程进入阻塞

        for (int i = 1; i <=6; i++) {
            new Thread(() -> {
                System.out.println("线程"+ Thread.currentThread().getName() + "执行完毕");
                countDownLatch.countDown(); //一个分支线程执行完毕，让计数器减1
            }, String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();  //计数器归0后，主线程被唤醒，继续往下执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程执行完毕");
    }
}
