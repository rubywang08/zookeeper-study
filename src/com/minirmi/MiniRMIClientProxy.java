package src.com.minirmi;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class MiniRMIClientProxy {
    private String host;
    private int port;

    public MiniRMIClientProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Object createProxy(Class clazz) {


        Object instance = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new RemoteInvocationHandler());
        return instance;
    }

    class RemoteInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            RemoteRequest remoteRequest = new RemoteRequest();
            remoteRequest.setMethodName(method.getName());
            remoteRequest.setParameters(args);
            remoteRequest.setParameterTypes(method.getParameterTypes());

            Object result = remoteCall(remoteRequest);
            return result;
        }

        private Object remoteCall(RemoteRequest remoteRequest) {
            Socket socket = null;
            try {
                socket = new Socket(host, port);

                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(remoteRequest);
                outputStream.flush();

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object result = inputStream.readObject();
                inputStream.close();
                outputStream.close();
                return result;
            } catch (Exception e) {
                throw new RuntimeException("remoteCall exception:",e);
            }finally {
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
