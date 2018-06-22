package src.com.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Connectiondemo {

    public static void main(String[] args) {
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            ZooKeeper zooKeeper = new ZooKeeper("192.168.204.128:2181", 4000, new Watcher() {
                @Override
                public void process(WatchedEvent event) {
                    countDownLatch.countDown();//收到服务端响应，表示连接成功
                    System.out.println("event" + event);
                }
            });
            System.out.println(zooKeeper.getState());
            countDownLatch.await();
            System.out.println(zooKeeper.getState());
            //create
//            zooKeeper.create("/ruby/second/third","0".getBytes() ,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT );

            //get
            Stat stat = new Stat();
            byte[] bytes = zooKeeper.getData("/ruby", null,stat );
            System.out.println(new String(bytes));

            //update
            zooKeeper.setData("/ruby","1".getBytes() ,stat.getVersion());
            byte[] bytes1 = zooKeeper.getData("/ruby", null,stat );
            System.out.println(new String(bytes1));

            //delete
            Stat stat2 = new Stat();
            byte[] bytes2 = zooKeeper.getData("/ruby/second/third", null,stat );
            zooKeeper.delete("/ruby/second/third", stat2.getVersion());
            Stat stat3 = new Stat();
            byte[] bytes3 = zooKeeper.getData("/ruby/second", null,stat );
            zooKeeper.delete("/ruby/second", stat3.getVersion());
            zooKeeper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
}
