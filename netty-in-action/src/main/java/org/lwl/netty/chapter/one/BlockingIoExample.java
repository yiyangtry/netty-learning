package org.lwl.netty.chapter.one;

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
public class BlockingIoExample {

    /**
     * 开启两个线程启动socket
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ServerSocket ss = new ServerSocket(8888);
        for (int i = 0; i < 2; i++) {
            executorService.execute(() -> {
                try {
                    System.out.println("启动服务器");
                    Socket s = ss.accept();
                    System.out.println("客户端:" + s.getInetAddress().getLocalHost() + "已连接到服务器");

                    BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    while (true) {
                        String mess = br.readLine();
                        System.out.println("读取客户端发送来的消息：" + mess);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
                        bw.write(mess + "\n");
                        bw.flush();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void serve(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("启动服务器....");
        Socket clientSocket = serverSocket.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        String request, response;
        while ((request = in.readLine()) != null) {
            if ("Done".equals(request)) {
                break;
            }
            response = processRequest(request);
            out.println(response);
        }
    }

    private String processRequest(String request) {
        return "Processed";
    }
}
