package k4;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 多线程并发下，对非线程安全的集合进行操作，会引发并发修改异常（ConcurrentModificationException）
 */
public class Test {
    public static void main(String[] args) {
        //List<String> list = new ArrayList<>();  //ArrayList是非线程安全的，会引发ConcurrentModificationException
        /**
         * 解决方法一：使用线程安全的Vector集合替换ArrayList
         * 不推荐使用Vector集合，因为该集合中的方法都是synchronized修饰的同步方法，这就意味着多线并发下，线程必须拿到锁之后才能执行
         * 也就是说同一时刻只能有一个线程执行，导致效率变低
         * List<String> list = new Vector<>();
         */

        /**
         * 方法二：使用Collections工具类中的synchronizedList方法，将非线程的安全的List集合转换成线程安全的List集合
         * List<String> list = Collections.synchronizedList(new ArrayList<>());
         */

        /**
         * 方法三：使用concurrent包下的CopyOnWriteArrayList集合（推荐）
         * CopyOnWrite写入时复制，保存快照
         */
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

        /**
         * 同理HashSet集合也是一个非线程安全的集合，一样可以用上述方法处理
         * hashset底层是hashmap，添加到hashset中的元素，会作为键，然后再给它一个对应值（这个值是一个final修饰Object对象），
         * 构成键值对后，被添加到底层的hashmap中
         */

        /**
         * HashMap也非线程安全的
         * 在多线程并发下，使用concurrent包下的ConcurrentHashMap集合（推荐）
         */
    }
}
