package src.com.distributedlock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 利用zookeeper watcher机制和zookeeper临时有序节点机制，实现分布式锁
 * 以zookeeper临时节点为锁，当线程启动立即创建一个临时有序节点，作为当前线程的锁。
 * 每次都以最小节点来判断线程是否成功拿到锁，因此线程能否获取到锁只需检查线程创建的节点是否是当前最小的节点。
 * 如果不是，则线程进入等待，等待自己前一个锁释放，即等待自己前一个节点删除，让自己成为最小的节点。
 * 因此，删除前一个节点后，需要通知等待的节点，这里使用zookeeper watcher机制，当线程进入等待时，
 * 设置对自己前一个节点进行监听。 前一个节点删除后，会调用watcher的process方法，让监听的线程停止等待，
 * 也就拿到了锁。
 */

public class DistrubutedLock implements Lock, Watcher {
    private ZooKeeper zk;
    private CountDownLatch countDownLatch;
    private String ROOT_LOCK;
    private String CURRENT_LOCK;
    private String WAIT_LOCK;

    public DistrubutedLock() {
        try {
            ROOT_LOCK = "/lock";
            zk = new ZooKeeper("192.168.204.128:2181,192.168.204.129:2181,192.168.204.130:2181",
                    4000, this);
            Stat stat = zk.exists(ROOT_LOCK, null);
            if (stat == null) {
                zk.create(ROOT_LOCK, "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean tryLock() {
        try {
            CURRENT_LOCK = zk.create(ROOT_LOCK + "/", "0".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.out.println(Thread.currentThread().getName() + "-> try to get lock " + CURRENT_LOCK);
            List<String> childs = zk.getChildren(ROOT_LOCK, null);
            SortedSet<String> sortedSet = new TreeSet<>();
            for (String child : childs) {
                sortedSet.add(ROOT_LOCK + "/" + child);
            }
            String firstNode = sortedSet.first();
            if (CURRENT_LOCK.equals(firstNode)) {
                return true;
            }
            SortedSet<String> headSet = ((TreeSet<String>) sortedSet).headSet(CURRENT_LOCK);
            if (!headSet.isEmpty()) {
                WAIT_LOCK = headSet.last();
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public void lock() {
        if (tryLock()) {
            System.out.println(Thread.currentThread().getName() + "-> get lock success " + CURRENT_LOCK);
            return;
        }
        waitForLock(WAIT_LOCK);
        System.out.println(Thread.currentThread().getName() + "-> get lock success");
    }

    private void waitForLock(String prev) {
        try {
            Stat stat = zk.exists(prev, true);
            if (stat != null) {
                countDownLatch = new CountDownLatch(1);
                System.out.println(Thread.currentThread().getName() + "-> wait for lock " + prev);
                countDownLatch.await();
                // re-judge if current_lock is the smallest node
                List<String> childs = zk.getChildren(ROOT_LOCK, null);
                SortedSet<String> sortedSet = new TreeSet<>();
                for (String child : childs) {
                    sortedSet.add(ROOT_LOCK + "/" + child);
                }
                String firstNode = sortedSet.first();
                if (!CURRENT_LOCK.equals(firstNode)) {
                    SortedSet<String> headSet = ((TreeSet<String>) sortedSet).headSet(CURRENT_LOCK);
                    if (!headSet.isEmpty()) {
                        WAIT_LOCK = headSet.last();
                    }
                    waitForLock(WAIT_LOCK);
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unlock() {
        try {
            zk.delete(CURRENT_LOCK, -1);
            System.out.println(Thread.currentThread().getName() + "-> unlock " + CURRENT_LOCK);
            CURRENT_LOCK = null;
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }

}
