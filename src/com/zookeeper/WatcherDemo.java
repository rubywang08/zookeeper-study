package src.com.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class WatcherDemo {
    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            final ZooKeeper zooKeeper = new ZooKeeper("192.168.204.128:2181,192.168.204.129:2181,192.168.204.130:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("default event: " + watchedEvent.getType());
                    if (Event.KeeperState.SyncConnected.equals(watchedEvent.getState())) {
                        countDownLatch.countDown();
                    }
                }
            });
            countDownLatch.await();

            //create znode
            zooKeeper.create("/testwatch", "xx".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            //bind event, via exists,getData,getChildren
            Stat stat = zooKeeper.exists("/testwatch", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("testwatch event: "+ watchedEvent.getType()+"->"+watchedEvent.getPath());
                    try {
                        //watcher是一次性操作，要一直触发事件需要重新绑定
                        zooKeeper.exists(watchedEvent.getPath(),this);
                    } catch (KeeperException e) {
                        e.printStackTrace();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            //trigger event
            stat = zooKeeper.setData("/testwatch", "xx-update".getBytes(), stat.getVersion());

            stat = zooKeeper.setData("/testwatch", "xx-update1".getBytes(), stat.getVersion());
            //delete
            zooKeeper.delete("/testwatch", stat.getVersion());
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
