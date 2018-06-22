package src.com.minirmiWithRegistration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

public class RegistrationImpl implements IRegistration {
    CuratorFramework curatorFramework;

    {
        curatorFramework = CuratorFrameworkFactory.builder().
                connectString(ZkConfig.CONNECT_STR).
                sessionTimeoutMs(4000).
                retryPolicy(new ExponentialBackoffRetry(1000, 3)).
                namespace("curator").build();
        curatorFramework.start();
    }

    @Override
    public void register(String interfaceName, String serviceAddress) {
        final String servicePath = ZkConfig.ZK_REGISTER_PATH + "/" + interfaceName;
        try {
            Stat stat = curatorFramework.checkExists().forPath(servicePath);
            if (stat == null) {
                curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(servicePath, "0".getBytes());
            }
            final String fullAddress = servicePath + "/" + serviceAddress;
            Stat stat1 = curatorFramework.checkExists().forPath(fullAddress);
            if (stat1 == null) {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(fullAddress);
            }
            System.out.println(fullAddress + " register success");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
