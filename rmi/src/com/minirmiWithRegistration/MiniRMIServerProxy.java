package src.com.minirmiWithRegistration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniRMIServerProxy {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    private Map<String, Object> serviceMap = new HashMap<>();
    private String serviceAddress;
    private IRegistration registration;

    public MiniRMIServerProxy(IRegistration registration, String serviceAddress) {
        this.registration = registration;
        this.serviceAddress = serviceAddress;
    }

    public void bind(Object... services) {
        for (Object service:services) {
            Class<?>[] clazz = service.getClass().getInterfaces();
            serviceMap.put(clazz[0].getName(),service );
            registration.register(clazz[0].getName(), serviceAddress);
        }
    }

    public void exportService() {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            String[] address = serviceAddress.split(":");
            serverSocket.bind(new InetSocketAddress(address[0], Integer.parseInt(address[1])));
            System.out.println("Server start");
            while (true) {
                Socket socket = serverSocket.accept();
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        processHandler(serviceMap, socket);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processHandler(final Map<String,Object> serviceMap, final Socket socket) {
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;
        try {
            inputStream = new ObjectInputStream(socket.getInputStream());
            RemoteRequest request = (RemoteRequest) inputStream.readObject();
            Object service = serviceMap.get(request.getClassName());
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParameterTypes());
            Object result = method.invoke(service, request.getParameters());
            System.out.println(service.getClass() + "." + request.getMethodName() + " " + "has been called");

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
