package src.com.mytest.multithread;

import java.util.Queue;
import java.util.Random;

public class Producer implements Runnable {
    private Queue<Integer> queue;
    private int maxSize;

    public Producer(Queue<Integer> queue, int maxSize) {
        this.queue = queue;
        this.maxSize = maxSize;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                if (queue.size() == maxSize) {
                    try {
                        System.out.println("Producer wait ");
                        queue.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Random random = new Random();
                int value = random.nextInt();
                queue.add(value);
                System.out.println("producer: " + Thread.currentThread().getId() + "生产-" + value);
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
