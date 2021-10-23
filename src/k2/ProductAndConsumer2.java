package k2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JUC来实现生产者和消费者
 * 主要使用到两个接口：Lock和Condition
 *  Lock替换synchronized方法和语句的使用， Condition取替换对象监视器方法（wait，notify，notifyAll）的使用。
 *  Condition对象的 await方法等价于 wait。signal方法等价于notify。signalAll等价于notifyALL
 */
public class ProductAndConsumer2 {
    public static void main(String[] args) {
        Data2 data2 = new Data2();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data2.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data2.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data2.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data2.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

class Data2 {
    private int number = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    //生产者
    public void increment() throws InterruptedException {
        lock.lock();

        try {
            while (number != 0) {  //注意：一定会要使用循环来判断
                condition.await(); //让当前线程释放占有的锁，进入阻塞状态。（进入阻塞状态的线程等待被唤醒）
            }
            number ++;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            condition.signalAll(); //唤醒正在等待获取当前对象锁的所有线程（被唤醒的线程解除阻塞状态）

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    //消费者
    public void decrement() throws InterruptedException {
        lock.lock();

        try {
            while (number == 0) {
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName() + "=>" + number);
            condition.signalAll();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}


