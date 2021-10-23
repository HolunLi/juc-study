package k6;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier从字面上的意思可以知道，这个类的中文意思是“循环栅栏”。大概的意思就是一个可循环利用的屏障。
 * 它的作用就是会让所有线程都等待执行完成后, 再继续执行下一个任务。
 *
 * 举个例子，就像生活中我们会约朋友们到某个餐厅一起吃饭，有些朋友可能会早到，有些朋友可能会晚到，但是这个餐厅规定必须等到所有人到齐之后才会让我们进去。
 * 这里的朋友们就是各个线程，餐厅就是 CyclicBarrier。
 */
public class Test2 {
    public static void main(String[] args) {
        /**
         * 创建CyclicBarrier，需要两个参数
         * 第一个参数是parties，表示参与的线程个数
         * 第二个参数是Runnable，这个参数的意思是最后一个到达的线程要做的任务
         */
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6, () -> {
            System.out.println("第" + Thread.currentThread().getName() + "个到的同学说：既然大家都到齐了，就进酒店吃饭吧");
        });

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    System.out.println("第" + Thread.currentThread().getName() + "个同学到了");
                    cyclicBarrier.await(); //虽然到了一个同学，但还没有到齐，继续等待（进入阻塞状态）
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, String.valueOf(i)).start();
        }
    }
}
