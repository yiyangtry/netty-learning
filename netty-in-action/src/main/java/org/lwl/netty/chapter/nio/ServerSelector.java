package org.lwl.netty.chapter.nio;

import lombok.extern.slf4j.Slf4j;
import org.lwl.netty.chapter.util.Util;
import utils.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yiyang
 * @date 2022/6/24 22:40
 * 非阻塞模式
 */
@Slf4j
public class ServerSelector {

    public static void main(String[] args) throws IOException {
        // 1. 创建selector 管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 2. 建立channel和selector之间的联系
        // selectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey serverSocketSelectionKey = serverSocketChannel.register(selector, 0, null);
        // 关注accept事件
        serverSocketSelectionKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", serverSocketSelectionKey);

        serverSocketChannel.bind(new InetSocketAddress(8080));

        while (true) {
            // 3. select 方法 没有事件发生 线程阻塞  有事件  线程才会恢复运行
            // select在事件未处理时，不会阻塞
            selector.select();

            // 4. 处理事件 selectedKeys 内部包含了所有发送的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey nextSelectionKey = iterator.next();
                // 处理key时 要从 selectedKeys 中删除  否则下次处理会有问题
                iterator.remove();
                log.debug("selectionKey: {}", nextSelectionKey);
                // 5. 区分事件类型

                if (nextSelectionKey.isAcceptable()) {
                    ServerSocketChannel currentServerSocketChannel = (ServerSocketChannel) nextSelectionKey.channel();
                    SocketChannel socketChannel = currentServerSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey socketChannelSelectionKey = socketChannel.register(selector, 0, null);
                    socketChannelSelectionKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}", socketChannelSelectionKey);

                    continue;
                }

                if (nextSelectionKey.isReadable()) {
                    try {
                        SocketChannel socketChannel = (SocketChannel) nextSelectionKey.channel(); // 拿到触发事件的channel
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        int read = socketChannel.read(buffer); // 如果是正常断开  read的返回值是 -1
                        if (read == -1) {
                            nextSelectionKey.cancel();
                            log.info("客户端已主动断开");
                            continue;
                        }
                        Util.split(buffer);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                        // e.printStackTrace();
                        nextSelectionKey.cancel();  // 客户端断开了  需要将 key从 selector 的key集合中删除
                    }
                }

            }

        }

    }

}
