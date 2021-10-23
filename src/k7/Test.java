package k7;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReadWriteLock 读写锁
 * 分为读锁（共享锁）和写锁（独占锁）
 * 共享锁：同一时刻，可以被多个线程占有
 * 独占锁：同一时刻，只能被一个线程占有
 */
public class Test {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.put(temp+"", temp);
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(() -> {
                myCache.get(temp+"");
            }, String.valueOf(i)).start();
        }
    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 使用写锁，保证同一时刻，只能有一个线程进行写入操作
     */
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "写入key");
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入key成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    /**
     * 读锁，可以被多个线程占有，同一时刻可以有多个线程进行读操作
     * 这里加读锁的目的是为了保证在写入数据的同时，避免又读取
     */
    public void get(String key) {
        readWriteLock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + "读取key");
            map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取key成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

}