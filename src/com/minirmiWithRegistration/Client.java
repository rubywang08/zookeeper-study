package src.com.minirmiWithRegistration;


public class Client {
    public static void main(String[] args) {
        IDiscovery discovery = new DiscoveryImpl();
        for (int i = 0; i < 10; i++) {
        String serviceAddress = discovery.discover(IHelloService.class.getName());
        MiniRMIClientProxy proxy = new MiniRMIClientProxy(serviceAddress);
        IHelloService helloService = (IHelloService) proxy.createProxy(IHelloService.class);
            System.out.println(helloService.sayHello("ruby1"));
        }
    }
}
