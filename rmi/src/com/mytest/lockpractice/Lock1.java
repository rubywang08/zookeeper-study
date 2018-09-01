package src.com.test.lockpractice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程交替打印12345，678910，直到75
 */
public class Lock1 {
    private static int num = 1;
    private static ReentrantLock lock = new ReentrantLock();
    private static int state = 1;

    public static void main(String[] args) {
        Condition c1 = lock.newCondition();
        Condition c2 = lock.newCondition();
        Condition c3 = lock.newCondition();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    lock.lock();
                    try {
                        if (state != 1) {
                            try {
                                c1.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < 5; j++) {
                            System.out.println("线程一" + "-" + num++);
                        }
                        state = 2;
                        c2.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    lock.lock();
                    try {
                        if (state != 2) {
                            try {
                                c2.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < 5; j++) {
                            System.out.println("线程二" + "-" + num++);
                        }
                        state = 3;
                        c3.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    lock.lock();
                    try {
                        if (state != 3) {
                            try {
                                c3.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < 5; j++) {
                            System.out.println("线程三" + "-" + num++);
                        }
                        state = 1;
                        c1.signal();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }

}
