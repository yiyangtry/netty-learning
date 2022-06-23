package org.lwl.netty.chapter.bio.test1;

import java.io.*;
import java.net.Socket;

/**
 * @author thinking_fioa
 * @createTime 2018/5/14
 * @description BIO 服务端例子, 代码清单 1-1 阻塞I/O示例
 */


public class Client {

    public static void main(String[] args) {
        try {

            // 1.创建Socket对象请求服务端的连接
            Socket socket = new Socket("127.0.0.1", 8888);

            //  2.从Socket对象中获取一个字节输出流
            OutputStream outputStream = socket.getOutputStream();

            // 3.把字节输出流包装成一个打印流
            PrintStream printStream = new PrintStream(outputStream);

            printStream.println("hello");
            printStream.flush();

            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
