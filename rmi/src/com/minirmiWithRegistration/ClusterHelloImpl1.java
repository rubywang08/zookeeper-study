package src.com.minirmiWithRegistration;

public class ClusterHelloImpl1 implements IHelloService {
    @Override
    public String sayHello(String msg) {
        return "8081 " + msg;
    }
}
