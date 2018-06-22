package src.com.minirmiWithRegistration.loadbalance;

import java.util.List;

public abstract class AbstactLoadBalance implements ILoadbalance {
    @Override
    public String selectHost(List<String> repos) {
        if (repos == null) {
            return null;
        }
        if (repos.size() == 1) {
            return repos.get(0);
        }
        return doSelect(repos);
    }

    protected abstract String doSelect(List<String> repos);
}
