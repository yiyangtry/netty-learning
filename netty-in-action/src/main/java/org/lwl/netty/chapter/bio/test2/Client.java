package org.lwl.netty.chapter.bio.test2;

import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author thinking_fioa
 * @createTime 2018/5/14
 * @description BIO 服务端例子, 代码清单 1-1 阻塞I/O示例
 */


public class Client {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);

            OutputStream os = s.getOutputStream();
            PrintStream printStream = new PrintStream(os);
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.print("请说: ");
                String msg = sc.nextLine();
                printStream.println(msg);
                printStream.flush();
            }


//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
//
//            //向服务器端发送一条消息
//            bw.write("client1\n");
//            bw.flush();

             // Thread.sleep(1000);

        } catch (Exception e ) {
            e.printStackTrace();
        }
    }

}
