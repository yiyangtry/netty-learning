package org.lwl.netty.chapter.nio.clientserver.writerserver;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author yiyang
 * @date 2022/6/24 22:40
 * write事件
 */
@Slf4j
public class WriterServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8080));

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();

            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                iter.remove();
                if (selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey sckey = socketChannel.register(selector, SelectionKey.OP_READ);
                    // 1. 向客户端发送内容
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 5000000; i++) {
                        sb.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());
                    int write = socketChannel.write(buffer);
                    // 3. write 表示实际写了多少字节
                    System.out.println("实际写入字节:" + write);
                    // 4. 如果有剩余未读字节，才需要关注写事件
                    if (!buffer.hasRemaining()) {
                        continue;
                    }

                    // read 1  write 4
                    // 在原有关注事件的基础上，多关注 写事件
                    sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
                    // 把 buffer 作为附件加入 sckey
                    sckey.attach(buffer);
                    continue;
                }

                if (selectionKey.isWritable()) {
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    int write = sc.write(buffer);
                    System.out.println("实际写入字节:" + write);
                    if (buffer.hasRemaining()) {
                        continue;
                    }
                    // 写完了
                    selectionKey.interestOps(selectionKey.interestOps() - SelectionKey.OP_WRITE);
                    selectionKey.attach(null);
                }
            }
        }
    }

}
