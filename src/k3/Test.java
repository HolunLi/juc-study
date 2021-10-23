package k3;

import java.util.concurrent.TimeUnit;

/**
 * 线程A和线程B操作的是同一个资源对象，所以会争抢同一把锁
 */
public class Test {
    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() -> {
            phone.sendMessage();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1); //主线程休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone.listenMusic();
        }, "B").start();

    }
}

/**
 * 线程A和线程B操作的是不同的资源对象，拿到的锁不是同一把锁
 */
class Test2 {
    public static void main(String[] args) {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            phone1.sendMessage();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1); //主线程休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            phone2.call();
        }, "B").start();

    }
}

class Phone {

    /**
     * synchronized关键字使用在非静态方法（实例方法）上，锁住的是调用该方法的当前对象，称为对象锁
     */
    public synchronized void sendMessage() {
        try {
            TimeUnit.SECONDS.sleep(3); //当前线程休眠3秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("发消息");
    }

    public synchronized void call() {
        System.out.println("打电话");
    }

    //注意：listenMusic方法没有用synchronized修饰，不是同步方法，不受锁的约束
    public void listenMusic() {
        System.out.println("听音乐");
    }
}

/**
 * 虽然线程A和线程B操作的不是同一个资源对象，但都是由一个类创建的
 * 因为driverCar方法上加synchronized，锁住的是class对象
 * 所以虽线程A和线程B，还是争抢同一把锁
 */
class Test3 {
    public static void main(String[] args) {
        Car car1 = new Car();
        Car car2 = new Car();

        new Thread(() -> {
            car1.driverCar();
        }, "A").start();

        try {
            TimeUnit.SECONDS.sleep(1); //主线程休眠1秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            car2.stopCar();
        }, "B").start();

    }
}

class Car {
    //synchronized使用在静态方法（类方法）上，锁住的是类对象（class对象），称为类锁，因为静态方法在类被加载时，就会被随之加载
    public static synchronized void driverCar() {
        try {
            TimeUnit.SECONDS.sleep(3); //当前线程休眠3秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "开车...");
    }

    public static synchronized void stopCar() {
        System.out.println(Thread.currentThread().getName() + "停车...");
    }
}