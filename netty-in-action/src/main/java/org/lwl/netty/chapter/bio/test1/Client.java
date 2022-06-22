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
            Socket s = new Socket("127.0.0.1", 8888);

            //构建IO
            OutputStream os = s.getOutputStream();

            PrintStream printStream = new PrintStream(os);
            printStream.println("hello");
            printStream.flush();

//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
//
//            //向服务器端发送一条消息
//            bw.write("client1\n");
//            bw.flush();

             Thread.sleep(1000);

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

}
