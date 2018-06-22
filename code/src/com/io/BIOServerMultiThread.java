package src.com.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServerMultiThread {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8080);
        while (true) {
            Socket socket = server.accept();
            RunImpl runimpl = new RunImpl(socket);
            runimpl.run();
        }

    }

    static class RunImpl implements Runnable {
        private Socket socket;

        public RunImpl(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {

            BufferedReader reader = null;
            try {

                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                    if (socket != null) {
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
