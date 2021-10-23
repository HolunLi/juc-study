package k2;

/**
 * 生产者和消费者
 * 拓展:
 * wait()、notify()和notifyAll()方法是本地方法，并且为final方法，无法被重写。
 * 调用某个对象的wait()方法能让当前占有该对象锁的线程释放其占有的锁，进入阻塞状态
 * 调用某个对象的notify()方法能够唤醒一个正在等待这个对象的锁的线程，如果有多个线程都在等待这个对象的锁，则只能唤醒其中一个线程；
 * 调用某个对象的notifyAll()方法能够唤醒所有正在等待这个对象的锁的线程；
 */
public class ProductAndConsumer {
    public static void main(String[] args) {
        Data data = new Data();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

     /*   new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();*/
    }
}

/**
 * 判断等待，业务，通知
 */
class Data {
    private int number = 0;

    //生产者
    public synchronized void increment() throws InterruptedException {
        while (number != 0) {  //注意：一定会要使用循环来判断
            this.wait(); //让当前线程释放占有的锁，进入阻塞状态。（进入阻塞状态的线程等待被唤醒）
        }
        number ++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        this.notifyAll(); //唤醒正在等待获取当前对象锁的所有线程（被唤醒的线程解除阻塞状态）
    }

    //消费者
    public synchronized void decrement() throws InterruptedException {
        while (number == 0) {
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        this.notifyAll();
    }

}