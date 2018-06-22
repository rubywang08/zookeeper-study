package src.com.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class CuratorDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("192.168.204.128:2181,192.168.204.129:2181,192.168.204.130:2181").
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("curator").build();
        curatorFramework.start();

        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath("/cnode1/cnode2","aa".getBytes());

        Stat stat= new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/cnode1/cnode2");

        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/cnode1/cnode2", "ss".getBytes());

        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/cnode1/cnode2");

        curatorFramework.close();


    }
}
