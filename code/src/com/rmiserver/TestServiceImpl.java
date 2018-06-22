package src.com.rmiserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TestServiceImpl extends UnicastRemoteObject implements ITestService {

    protected TestServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String test(String msg) throws RemoteException {
        return msg;
    }
}
