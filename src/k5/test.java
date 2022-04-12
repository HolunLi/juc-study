package k5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程的第三种方式
 * 实现callable接口，重写call方法
 * Callable接口类似于Runnable接口 ，它们都是为了让其实例可以由另一个线程执行而设计的。
 * 然而，Runnable不返回结果，也不能抛出被检查的异常，而callable可以返回结果，也可以抛出异常
 */
public class test {
    public static void main(String[] args) {
        //FutureTask是适配类，它实现了RunnableFuture接口，RunnableFuture是Runnable的直接子接口
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());
        new Thread(futureTask, "a").start(); //开启线程

        try {
            String s = futureTask.get();//获取callable返回值，get方法可能会产生阻塞，放在最后执行
            System.out.println(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class MyThread implements Callable<String> {

    /**
     * call方法等同于runnable接口中的run方法，只不过call方法可以返回结果，也可以抛出异常
     */
    @Override
    public String call() throws Exception {
        System.out.println("haha");
        return "666";
    }

}