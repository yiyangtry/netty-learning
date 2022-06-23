package org.lwl.netty.chapter.bio.test3;

import java.io.*;
import java.net.Socket;

/**
 * 客户端2
 */
public class Client_2 {

    public static void main(String[] args) {
        try {
            Socket s = new Socket("127.0.0.1", 8888);

            //构建IO
            OutputStream outputStream = s.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            while (true) {
                //向服务器端发送一条消息
                bufferedWriter.write("client2\n");
                bufferedWriter.flush();
                Thread.sleep(1000);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
