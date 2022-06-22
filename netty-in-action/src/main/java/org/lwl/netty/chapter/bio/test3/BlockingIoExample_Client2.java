package org.lwl.netty.chapter.bio.test3;

import java.io.*;
import java.net.Socket;

/**
 * @author thinking_fioa
 * @createTime 2018/5/14
 * @description BIO 服务端例子, 代码清单 1-1 阻塞I/O示例
 */


public class BlockingIoExample_Client2 {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);

            //构建IO
            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

            while (true) {
                //向服务器端发送一条消息
                bw.write("client2\n");
                bw.flush();

                //读取服务器返回的消息
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String mess = br.readLine();
                System.out.println("收到服务器的消息：" + mess);

                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
