package k8;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * BlockingQueue 阻塞队列
 * Collection接口有三大子接口，分别是：List，Set，Queue
 * BlockingQueue 是 Queue的子接口
 * BlockingQueue 最常用的2个实现类是：ArrayBlockingQueue 和 LinkedBlockingQueue
 */
public class Test {
    public static void main(String[] args) {
        BlockingQueue<Object> blockingQueue = new ArrayBlockingQueue<>(3); //指定队列的容量（底层数组的长度）

        /**
         * 阻塞队列有四套常用的api（牢记）
         */

/*
        //add（进队） 和 remove（出队），抛出异常
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.add(3);
        //blockingQueue.add(4);  队列大小为3，只允许三个元素入队，队满的情况下在入队，会抛出异常

        blockingQueue.element(); //取出队头元素

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove()); //队列为空，出队失败，抛出异常
 */

/*
        //offer（进队） 和 poll（出队），不抛出异常
        blockingQueue.offer(1);
        blockingQueue.offer(2);
        blockingQueue.offer(3);
        //blockingQueue.offer(4);  堆满，无法入队，不会抛出异常

        System.out.println(blockingQueue.peek()); //取出队头元素

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        //System.out.println(blockingQueue.poll()); //队列为空，出队失败，返回null, 不会抛出异常
 */

/*
        //put（进队） 和 take（出队）, 阻塞，一直等待
        try {
            blockingQueue.put(1);
            blockingQueue.put(2);
            blockingQueue.put(3);
            //blockingQueue.put(4);  //堆满，无法入队，线程会进入阻塞状态，直到出队了一个元素才会解除阻塞
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            System.out.println(blockingQueue.take());
            //System.out.println(blockingQueue.take()); //堆满，无法出队，线程会进入阻塞状态，直到入队了一个元素才会解除阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
 */

        //offer(,,,)（进队） 和 poll(,,)（出队），阻塞，超时不等待
        try {
            blockingQueue.offer(1);
            blockingQueue.offer(2);
            blockingQueue.offer(3);
            blockingQueue.offer(4, 3, TimeUnit.SECONDS); //堆满，无法入队，线程会进入阻塞状态，超过3秒还没有元素出队就解除阻塞

            System.out.println(blockingQueue.poll());
            System.out.println(blockingQueue.poll());
            System.out.println(blockingQueue.poll());
            System.out.println(blockingQueue.poll(3, TimeUnit.SECONDS)); //堆满，无法出队，线程会进入阻塞状态，超过3秒还没有元素入队就解除阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}