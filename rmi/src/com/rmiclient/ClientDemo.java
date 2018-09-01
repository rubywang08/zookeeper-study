package src.com.rmiclient;

import src.com.rmiserver.ITestService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientDemo {
    public static void main(String[] args) {
        try {
            //这里的ITestServce应该是在另一个工程的同一个包下，此处只是为了方便。
            //client端的ITestService的全限定名必须和server端的相同，才能强转成功
            //发布一个服务后，通常是提供有接口的Jar包给客户端
            ITestService service= (ITestService) Naming.lookup("rmi://127.0.0.1/com.mytest");
            System.out.println(service.test("hello"));

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
