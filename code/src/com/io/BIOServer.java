package src.com.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        BufferedReader reader = null;
        try {
            ServerSocket server = new ServerSocket(8080);
            socket = server.accept();//等待客户端连接
            //获取输入流
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (socket != null) {
                socket.close();
            }
        }

    }


}
