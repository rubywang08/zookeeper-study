package src.com.minirmi;

public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String msg) {
        return "hello " + msg;
    }
}
