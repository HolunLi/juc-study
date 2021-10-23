package k1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lambada表达式
 * () -> {}
 */
public class SaleTicketTest {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        //多个线程操作同一个资源
        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                ticket.saleTicket();
            }
        }, "C").start();
    }
}

/**
 * 资源类
 */
class Ticket {
    private int number = 100;
    private final Lock lock = new ReentrantLock(); //创建锁

    /**
     * 解决多线程并发产生问题：
     * 方法一：直接在saleTicket方法上，加上synchronized关键字，变为同步方法。主要是为调用该方法的对象加锁，各线程会排队执行这个方法
     */
    /*
    public synchronized void saleTicket() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName()+ "买了1张票，还剩" + --number + "张票");
        }
    }
    */

    /**
     * 方法二：通过Lock对象为saleTicket方法加锁（实际是为调用该方法的对象加锁），这样每个线程拿到了锁后，才能执行这个方法，执行完毕再将锁释放，保证了各线程之间轮流执行
     * Lock是java.util.concurrent.locks包下提供的接口
     * 该接口主要三个实现类，分别是：ReentrantLock（可重入锁），ReentrantReadWriteLock.ReadLock（读锁） ， ReentrantReadWriteLock.WriteLock（写锁）
     * Lock三部曲
     * 创建锁
     * 加锁
     * 解锁
     */
    public synchronized void saleTicket() {
        lock.lock(); //加锁

        //saleTicket方法上锁后，业务代码要放在try语句中
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()+ "买了1张票，还剩" + --number + "张票");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock(); //解锁
        }
    }

}