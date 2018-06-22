package src.com.minirmi;

public class Client {
    public static void main(String[] args) {
        MiniRMIClientProxy proxy = new MiniRMIClientProxy("127.0.0.1", 8080);
        IHelloService helloService = (IHelloService) proxy.createProxy(IHelloService.class);
        System.out.println(helloService.sayHello("ruby"));
        System.out.println(helloService.sayHello("Cris"));
    }
}
