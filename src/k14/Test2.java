package k14;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * javap -c Test2.class 对Test2.class字节码文件，进行反编译
 */
public class Test2 {
   /*
    private volatile static int num = 0; //volatile 不能保证原子性

    public static void add() {   //除了在add方法上加synchronized或Lock，可以保证原子性外，还可以使用原子类（原子类位于concurrent包下的子包atomic中）
        num++;
        *//**
         * 使用javap -c Test2.class命令 对Test2.class字节码文件，进行反编译，可以查看到
         * num++ 不是一个原子性操作，它包括三步
         * 先读取num的值
         * 再进行加1
         * 再重新赋予num
         *//*
    }
    */

    private static AtomicInteger num = new AtomicInteger();

    public static void add() {
        num.getAndIncrement();  //num+1
    }

    public static void main(String[] args) {
        //开启了20个线程，每个线程都对num值进行1000次累加，所以理论上num最终结果应该为2万，但实际测试结果，并不能保证每次都为2万（使用原子类可以解决这个问题）
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    add();
                }
            }).start();
        }

        //当活动的线程数量大于2时，让出主线程的cpu使用权
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " " + num);
    }
}