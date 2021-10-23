package k14;

import java.util.concurrent.TimeUnit;

/**
 * Volatile 是 Java 虚拟机提供轻量级的同步机制，特征如下：
 * 1.保证可见性
 *    可见性是指：多个线程访问同一个变量时，一个线程修改这个变量的值，其
 *    它线程能够感知到变量值的变化。
 * 2.不保证原子性
 * 3.禁止/防止指令重排
 */
public class Test {
    private volatile static int number; //在共享变量上加Volatile，可以保证可见性

    public static void main(String[] args) {

        new Thread(()->{
            while (number == 0) {   //线程A，感知不到number值已修改，会一直执行

            }
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        number = 1;  //主线程中对number值进行修改
        System.out.println(number);
    }
}
