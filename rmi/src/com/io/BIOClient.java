package src.com.io;

import java.io.*;
import java.net.Socket;

public class BIOClient {
    public static void main(String[] args) throws IOException {
        Thread t1 = new Thread(new RunImpl());
        Thread t2 = new Thread(new RunImpl());
        t1.start();
        t2.start();
    }


    static class RunImpl implements Runnable {

        @Override
        public void run() {
            Socket socket = null;
            PrintWriter writer = null;

            try {
                socket = new Socket("127.0.0.1", 8080);
                writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.println("hello");
                writer.flush();
                System.out.println(Thread.currentThread().getId()+"client写完了");
            } catch (IOException e) {
                e.printStackTrace();
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
