package src.com.minirmiWithRegistration.loadbalance;

import java.util.List;
import java.util.Random;

public class RandomLoadbalance extends AbstactLoadBalance {
    @Override
    protected String doSelect(List<String> repos) {
        Random random = new Random();
        int i = random.nextInt(repos.size());
        return repos.get(i);
    }
}
