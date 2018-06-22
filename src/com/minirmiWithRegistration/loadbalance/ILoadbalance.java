package src.com.minirmiWithRegistration.loadbalance;

import java.util.List;

public interface ILoadbalance {
    String selectHost(List<String> repos);
}
