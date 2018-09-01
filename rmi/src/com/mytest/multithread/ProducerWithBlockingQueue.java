package src.com.mytest.multithread;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class ProducerWithBlockingQueue implements Runnable{
    private BlockingQueue<Integer> queue;

    public ProducerWithBlockingQueue(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            Random random = new Random();
            int value = random.nextInt();
            System.out.println("producer: " + Thread.currentThread().getId() + "生产-" + value);
            try {
                queue.put(value);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
