package src.com.distributedlock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Curator是对zookeeper原生API的封装，用起比较方便，自己也实现了分布式锁，下面代码是其中一种锁InterProcessMutex
 */
public class CuratorDemo {

    public static void main(String[] args) {
        final CuratorFramework curatorFramework= CuratorFrameworkFactory.builder().
                connectString("192.168.204.128:2181,192.168.204.129:2181,192.168.204.130:2181").
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("curator").build();
        curatorFramework.start();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, "/lock");
                    try {
                        interProcessMutex.acquire();
                        System.out.println(Thread.currentThread().getName());
                        interProcessMutex.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "Thread" + i);
            thread.start();
        }

    }
}
