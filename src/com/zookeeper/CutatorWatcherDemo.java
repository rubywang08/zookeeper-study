package src.com.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * PathChildCache 监听一个节点下子节点的创建、删除、更新
 * NodeCache  监听一个节点的更新和创建事件
 * TreeCache  综合PatchChildCache和NodeCache的特性
 */
public class CutatorWatcherDemo {
    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString("192.168.204.128:2181,192.168.204.129:2181,192.168.204.130:2181").
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("curator").build();
        curatorFramework.start();

/*        addListenerWithNodeCache(curatorFramework,"/cnode1");
        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/cnode1");
        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/cnode1", "listen2".getBytes());*/

/*        addListenerWithChildNodeCache(curatorFramework, "/cnode1");
        curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath("/cnode1/cnode3", "newChild".getBytes());
        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/cnode1/cnode3");
        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/cnode1/cnode3", "listen2".getBytes());
        curatorFramework.delete().deletingChildrenIfNeeded().forPath("/cnode1/cnode3");//no event？*/

        addListenerWithTreeCache(curatorFramework, "/cnode1");
        Stat stat = new Stat();
        curatorFramework.getData().storingStatIn(stat).forPath("/cnode1");
        curatorFramework.setData().withVersion(stat.getVersion()).forPath("/cnode1", "listen2".getBytes());
        //要发送两个event ??
        // treecache: NODE_ADDED/cnode1
        //treecache: NODE_UPDATED/cnode1

        System.in.read();
    }

    public static void addListenerWithTreeCache(CuratorFramework curatorFramework, String path) throws Exception {
        TreeCache treeCache = new TreeCache(curatorFramework, path);
        TreeCacheListener treeCacheListener = new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("treecache: " + event.getType() + event.getData().getPath());
            }
        };

        treeCache.getListenable().addListener(treeCacheListener);
        treeCache.start();
    }

    private static void addListenerWithChildNodeCache(CuratorFramework curatorFramework, final String path) throws Exception {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                System.out.println("pathChildrenEvent: " + pathChildrenCacheEvent.getData().getPath());
                System.out.println("pathChildrenEvent: " + pathChildrenCacheEvent.getType());
            }
        };
        cache.getListenable().addListener(listener);
        cache.start(PathChildrenCache.StartMode.NORMAL);
    }

    private static void addListenerWithNodeCache(CuratorFramework curatorFramework, String path) throws Exception {
        final NodeCache nodeCache = new NodeCache(curatorFramework, path);
        NodeCacheListener nodeCacheListener = new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println("NodeCache event: " + nodeCache.getCurrentData());
                System.out.println("NodeCache event: " + nodeCache.getPath());
            }
        };
        nodeCache.getListenable().addListener(nodeCacheListener);
        nodeCache.start();

    }
}
