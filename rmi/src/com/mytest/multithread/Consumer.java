package src.com.test.multithread;

import java.util.Queue;

public class Consumer implements Runnable {
    private Queue<Integer> queue;
    private int maxSize;

    public Consumer(Queue queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                if (queue.isEmpty()) {
                    try {
                        System.out.println("Consumer "+ Thread.currentThread().getName() +" wait ");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Integer value = queue.poll();
                System.out.println("Consumer: " + Thread.currentThread().getName() + "消费-" + value);
                queue.notifyAll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
