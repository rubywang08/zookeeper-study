package src.com.minirmi;

import java.io.IOException;

public class Server {

    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        MiniRMIServerProxy rmi = new MiniRMIServerProxy("127.0.0.1", 8080);
        System.out.println("服务启动");
        rmi.exportService(helloService);
    }
}
