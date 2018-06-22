package src.com.minirmiWithRegistration;

public class Server {

    public static void main(String[] args) {
        IHelloService helloService = new HelloServiceImpl();
        IRegistration registrationCenter = new RegistrationImpl();
        MiniRMIServerProxy rmi = new MiniRMIServerProxy(registrationCenter,"127.0.0.1:8080");
        rmi.bind(helloService);
        rmi.exportService();
}
}
