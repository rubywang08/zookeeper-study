package src.com.rmiserver;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ITestService service = new TestServiceImpl();//创建了一个远程对象
            Naming.rebind("rmi://127.0.0.1/com.test", service);//注册中心 key - value
            System.out.println("test服务已启动");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }
}
