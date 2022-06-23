package org.lwl.netty.chapter.bio.test1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author thinking_fioa
 * @createTime 2018/5/14
 * @description BIO 服务端例子, 代码清单 1-1 阻塞I/O示例
 */
public class Server {

    /**
     * 开启两个线程启动socket
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // 1.定义一个ServerSocket对象进行服务端的端口注册
        ServerSocket ss = new ServerSocket(8888);
        System.out.println("启动服务器");
        // 2.监听客户端的Socket连接请求
        Socket socket = ss.accept();
        System.out.println("客户端:" + socket.getInetAddress().getLocalHost() + "已连接到服务器");

        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String msg;
        if ((msg = br.readLine()) != null) {
            System.out.println("服务端收到消息" + msg);
        }
    }
}
