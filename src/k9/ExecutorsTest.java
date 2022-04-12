package k9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors是一个工具类
 * Executors类中有3个创建线程池的静态方法
 * 分别是newFixedThreadPool、newSingleThreadExecutor 和 newCachedThreadPool();
 * 这3个方法实际上，都是new ThreadPoolExecutor对象将其返回
 * 可见实现线程池的核心就是ThreadPoolExecutor
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        //创建固定线程数的线程池（适用于执行长期任务,性能会更好）
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
        //创建只有线程的线程池（适用于任务一个个执行的场景）
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        //创建包含N个线程数的线程池（适用于执行很多短期异步的小程序或者负载较轻服务器）
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        try {
            //线程池的最大承载=阻塞队列容量+最大线程数
            for (int i = 1; i <= 9; i++) {
                threadPool2.execute(() -> { //threadPool.execute() 从线程池中获取线程来执行任务
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception  e) {
            e.printStackTrace();
        } finally {
            //从线程池中获取的线程都执行完毕，就关闭线程池
            threadPool2.shutdown();
        }
    }
}
