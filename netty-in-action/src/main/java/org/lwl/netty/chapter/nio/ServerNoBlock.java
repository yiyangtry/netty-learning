package org.lwl.netty.chapter.nio;

import lombok.extern.slf4j.Slf4j;
import utils.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yiyang
 * @date 2022/6/24 22:40
 */
@Slf4j
public class ServerNoBlock {

    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式, 单线程
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1. 创建了服务器
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        // 设置为非阻塞模式
        serverSocketChannel.configureBlocking(false); // 非阻塞模式

        // 2. 绑定监听端口
        serverSocketChannel.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接， SocketChannel 用来与客户端之间通信
            // log.debug("connecting...");
            SocketChannel socketChannel = serverSocketChannel.accept(); // 阻塞方法，线程停止运行  如果没有建立连接，sc为null
            if (socketChannel != null) {
                log.debug("connected... {}", socketChannel);
                socketChannel.configureBlocking(false); // 非阻塞模式
                channels.add(socketChannel);
            }
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                int read = channel.read(buffer);// 非阻塞  如果没有读到数据 read 返回0
                if (read <= 0) {
                    continue;
                }
                log.debug("before read... {}", channel);
                buffer.flip();
                ByteBufferUtil.debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }

}
