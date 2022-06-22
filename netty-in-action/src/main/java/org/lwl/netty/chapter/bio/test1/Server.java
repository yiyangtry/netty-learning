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
        ServerSocket ss = new ServerSocket(8888);
        try {
            System.out.println("启动服务器");
            Socket s = ss.accept();
            System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String msg;
            if ((msg = br.readLine()) != null) {
                System.out.println("服务端收到消息" + msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
