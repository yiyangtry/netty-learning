package org.lwl.netty.chapter.bio.test2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author thinking_fioa
 * @createTime 2018/5/14
 * @description BIO 服务端例子, 代码清单 1-1 阻塞I/O示例
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8888);
        try {
            System.out.println("启动服务器");
            Socket s = ss.accept();
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
