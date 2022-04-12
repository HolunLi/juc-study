package k9;

import java.util.concurrent.*;

/**
 * 创建线程池（从线程池中获取线程对象）
 * 线程池的七大参数
 * new ThreadPoolExecutor(int corePoolSize,  //核心线程数，也就是线程池中常驻线程数（线程池中最起码要有的线程数量）
 *                    int maximumPoolSize,  //最大线程数量，也就是线程池中能容纳同时执行的最大线程数
 *                    long keepAliveTime, //空闲线程的存活时间（当处于空闲状态的线程，超过这个时间，就会被释放）
 *                    TimeUnit unit, //存活时间的单位（秒，分）
 *                    BlockingQueue<Runnable> workQueue, //阻塞队列
 *                    ThreadFactory threadFactory,  //创建线程池中的线程使用的工厂
 *                    RejectedExecutionHandler handler) //拒接政策
 *
 * 四大拒绝策略
 * 1. AbortPolicy  //当要执行的任务数量，超过了线程池的最大承载，就抛出异常
 * 2. CallerRunsPolicy //
 * 3. DiscardPolicy //当要执行的任务数量，超过了线程池的最大承载，就不执行超出的任务，将其丢弃
 * 4. DiscardOldestPolicy //将阻塞队列中，最前面的任务丢弃（出队），重新尝试执行当前超出的任务
 */
public class Test {
    public static void main(String[] args) {

        /**
         * 当5个人需要执行任务时，2个人从线程池中获取到线程，开始执行任务，执行完后会将线程放回线程池中，其余3个人在阻塞队列中排队等待
         * 当8个人需要执行任务时，5个人从线程池中获取到线程，开始执行任务，执行完后会将线程放回线程池中，其余3个人在阻塞队列中排队等待
         *
         * 最大线程数该如何定义？
         * CPU密集型   cpu是几核，最大线程就是几，可以保持cpu的效率最高
         * IO密集型  判断程序有几个十分耗费IO的线程，则最大线程数大于这个数量即可
         *
         */
        System.out.println(Runtime.getRuntime().availableProcessors()); //获取CPU核数

        ExecutorService threadPool = new ThreadPoolExecutor(//创建线程池（初始线程数2，最大线程数3）
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try {
            //线程池的最大承载=阻塞队列容量+最大线程数
            for (int i = 1; i <= 9; i++) {
                threadPool.execute(() -> { //threadPool.execute() 从线程池中获取线程来执行任务
                    System.out.println(Thread.currentThread().getName() + " ok");
                });
            }
        } catch (Exception  e) {
            e.printStackTrace();
        } finally {
            //从线程池中获取的线程都执行完毕，就关闭线程池
            threadPool.shutdown();
        }
    }

}
