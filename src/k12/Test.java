package k12;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

/**
 * 单例模式 (饿汉式单例、懒汉式单例)，每次从容器中获取的bean，都是同一个
 * 单例模式，可以被反射机制打破
 */
public class Test {
    public static void main(String[] args) throws Exception {

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Hungry hungry = Hungry.getInstance();
                System.out.println(hungry);
            }).start();
        }

        System.out.println("=======================");

        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Lazy lazy = Lazy.getInstance();
                System.out.println(lazy);
            }).start();
        }

        TimeUnit.SECONDS.sleep(3);

        System.out.println("=======================");

        Lazy lazy1 = Lazy.getInstance();
        //反射机制可以打破单例模式
        Constructor<Lazy> constructor = Lazy.class.getDeclaredConstructor(null);
        constructor.setAccessible(true); //关闭访问控制权限检测
        Lazy lazy2 = constructor.newInstance();

        System.out.println(lazy1);
        System.out.println(lazy2); //lazy1和lazy2不是同一个对象，单例模式被反射机制打破
    }
}

/**
 * 饿汉式单例（饿汉式单例，在多线程并发的情况下，不会发生获取的实例不是同一个实例的情况，但时存在浪费存储空间的问题）
 */
class Hungry {
    private static final Hungry HUNGRY = new Hungry();

    private Hungry() {

    }

    public static Hungry getInstance() {
        return HUNGRY;
    }
}

/**
 * 懒汉式单例（懒汉式单例，在多线程并发的情况下，会发生获取的实例不是同一个实例的情况）
 */
class Lazy{
    private volatile static Lazy lazy; //双重检测锁模式的懒汉式单例，使用volatile，防止指令重排

    private Lazy() {

    }

    public static Lazy getInstance() {
        //双重检测锁模式的懒汉式单例（DCL懒汉式），在多线程并发的情况下，不会发生获取的实例不是同一个实例的情况
        if (lazy == null) {
            synchronized (Lazy.class) {
                if (lazy == null)
                    return lazy = new Lazy();
            }
        }

        return lazy;
    }
}