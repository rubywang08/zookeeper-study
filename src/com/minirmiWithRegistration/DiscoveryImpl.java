package src.com.minirmiWithRegistration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import src.com.minirmiWithRegistration.loadbalance.RandomLoadbalance;

import java.util.List;

public class DiscoveryImpl implements IDiscovery {
    List<String> repos = null;

    @Override
    public String discover(String interfaceName) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECT_STR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("curator").build();
        curatorFramework.start();
        final String path = ZkConfig.ZK_REGISTER_PATH+"/"+interfaceName;

        try {
            repos = curatorFramework.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //动态感知服务上下线
        addWatcher(path,curatorFramework);

        //负载均衡机制
        RandomLoadbalance loadbalance = new RandomLoadbalance();
        return loadbalance.selectHost(repos);
    }

    private void addWatcher(String path,CuratorFramework curatorFramework) {
        PathChildrenCache cache = new PathChildrenCache(curatorFramework, path, true);
        PathChildrenCacheListener listener = new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                //子节点变化，重置repos的值
                repos =curatorFramework.getChildren().forPath(path);
            }
        };
        cache.getListenable().addListener(listener);
        try {
            cache.start(PathChildrenCache.StartMode.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
