package src.com.mytest.multithread;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Test {
    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxSize = 10;
//        test1(queue,maxSize);
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);
        test2(blockingQueue);
//        System.out.println(16>>2);
    }


    public  static void test1(Queue<Integer> queue, int maxSize){
        new Thread(new Consumer(queue, maxSize)).start();
        new Thread(new Consumer(queue, maxSize)).start();
        System.out.println("main thread");
        new Thread(new Producer(queue, maxSize)).start();
    }

    public static void test2(BlockingQueue<Integer> blockingQueue){
        new Thread(new ProducerWithBlockingQueue(blockingQueue)).start();
        System.out.println("main thread");
        new Thread(new ConsumerWithBlockingQueue(blockingQueue)).start();
        new Thread(new ConsumerWithBlockingQueue(blockingQueue)).start();
    }
}
