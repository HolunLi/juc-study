package k6;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 *  Semaphore，信号量
 *  作用: 限制同时访问特定资源的线程数量，通过协调各个线程，以保证合理的使用资源。
 *
 *  可以把它简单的理解成我们停车场入口立着的那个显示屏，每有一辆车进入停车场显示屏就会显示剩余车位减1，
 *  每有一辆车从停车场出去，显示屏上显示的剩余车辆就会加1，当显示屏上的剩余车位为0时，停车场入口的栏杆就不会再打开，
 *  车辆就无法进入停车场了，直到有一辆车从停车场出去为止。
 */
public class Test3 {
    public static void main(String[] args) {
        /**
         * 假如一个厕所只有3个坑位，因此限制相同时间只能有3个人同时拉屎，当其中一个人拉完时，其它人才能
         */
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire(); //获取令牌（锁），也就是拥有了当前资源的访问权限（在获取到令牌之前，线程一直处理阻塞状态）

                    System.out.println("第" + Thread.currentThread().getName() + "个人，进入厕所拉屎");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("第" + Thread.currentThread().getName() + "个人拉完了，走出厕所");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release(); //执行完毕，释放其占有的令牌（锁）
                }
            }, String.valueOf(i)).start();
        }
    }

}
