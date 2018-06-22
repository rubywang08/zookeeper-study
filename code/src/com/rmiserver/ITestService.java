package src.com.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ITestService extends Remote {

    String test(String msg) throws RemoteException;
}
