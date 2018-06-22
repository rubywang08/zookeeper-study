package src.com.io;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter writer = null;
        try {
            socket = new Socket("127.0.0.1", 8080);
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println("hello");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket!=null){
                socket.close();
            }
        }
    }
}
