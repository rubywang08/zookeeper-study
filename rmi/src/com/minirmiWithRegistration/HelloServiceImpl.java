package src.com.minirmiWithRegistration;

public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String msg) {
        return "8080 " + msg;
    }
}
