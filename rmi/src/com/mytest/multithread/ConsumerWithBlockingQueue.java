package src.com.test.multithread;

import javafx.beans.binding.When;

import java.util.concurrent.BlockingQueue;

public class ConsumerWithBlockingQueue implements Runnable{
    private BlockingQueue<Integer> queue;

    public ConsumerWithBlockingQueue(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true){
            try {
                Integer value = queue.take();
                System.out.println("Consumer: " + Thread.currentThread().getId() + "消费-" + value);
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
