package org.lwl.netty.chapter.bio.test3;

import java.io.BufferedReader;
import java.io.IOException;
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

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        while (true) {
            Socket socket = ss.accept();
        }

        try {
            System.out.println("启动服务器");

            System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("服务端收到消息: " + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
