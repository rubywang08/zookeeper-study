package src.com.minirmiWithRegistration;

public class ClusterServer2 {

    public static void main(String[] args) {
        IHelloService helloService = new ClusterHelloImpl1();
        IRegistration registrationCenter = new RegistrationImpl();
        MiniRMIServerProxy rmi = new MiniRMIServerProxy(registrationCenter,"127.0.0.1:8081");
        rmi.bind(helloService);
        rmi.exportService();
}
}
