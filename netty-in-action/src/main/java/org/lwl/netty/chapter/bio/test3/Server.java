package org.lwl.netty.chapter.bio.test3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 目标: Socket网络编程。
 *  功能1：客户端可以反复发，一个服务端可以接收无数个客户端的消息！！
 *  小结：
 *  服务器如果想要接收多个客户端，那么必须引入线程，一个客户端一个线程处理！！
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        while (true) {
            Socket socket = ss.accept();
            System.out.println("客户端:" + socket.getInetAddress().getLocalHost() + "已连接到服务器");
            executorService.execute(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String msg;
                    while ((msg = bufferedReader.readLine()) != null) {
                        System.out.println("服务端接收到消息:" + msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
