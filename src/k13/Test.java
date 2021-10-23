package k13;

import java.util.concurrent.TimeUnit;

/**
 * 死锁（当两个线程都想获取对方占有的锁，然而两者都没将其占有的锁释放时，就会出现死锁）
 * 排查死锁的方法
 * jps -l //查看当前正1在运行java程序，找到可能出现死锁的程序，占用的进程号
 * jstack + 进程号 //在使用jstack命令，显示当前java进程的堆栈信息，查看是否出现锁
 */
public class Test {
    public static void main(String[] args) {
        new Thread(new MyThread("A", "B"), "T1").start();
        new Thread(new MyThread("B", "A"), "T2").start();
    }
}

class MyThread implements Runnable {
    private String lockName1; //字符串存储在字符串常量池中，线程之间共享常量池
    private String lockName2;

    public MyThread(String lockName1, String lockName2) {
        this.lockName1 = lockName1;
        this.lockName2 = lockName2;
    }

    @Override
    public void run() {
        synchronized(lockName1) {
            System.out.println("线程" + Thread.currentThread().getName() + " 拿到锁" + lockName1);

            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized(lockName2) {
                System.out.println("线程" + Thread.currentThread().getName() + " 拿到锁" + lockName2);
            }
        }
    }
}