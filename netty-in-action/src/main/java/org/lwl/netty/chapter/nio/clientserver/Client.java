package org.lwl.netty.chapter.nio.clientserver;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @author yiyang
 * @date 2022/6/24 22:50
 */
public class Client {

    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        // sc.write(Charset.defaultCharset().encode("hello\nworld\n"));
        sc.write(Charset.defaultCharset().encode("0123ab456\n"));
        // sc.write(Charset.defaultCharset().encode("0123456789abcdefg1234asdasddsfsdfsdfsdfqweqwe4213423456\n"));
        System.in.read();
    }
}
