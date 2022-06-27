package org.lwl.netty.chapter.nio.clientserver;

import lombok.extern.slf4j.Slf4j;
import org.lwl.netty.chapter.utils.Util;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yiyang
 * @date 2022/6/24 22:40
 * 非阻塞模式
 */
@Slf4j
public class ServerSelectorWorker {

    public static void main(String[] args) throws IOException {
        // 1. 创建selector 管理多个channel
        Selector boss = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // 2. 建立channel和selector之间的联系
        // selectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey serverSocketSelectionKey = serverSocketChannel.register(boss, 0, null);
        // 关注accept事件
        serverSocketSelectionKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key: {}", serverSocketSelectionKey);

        serverSocketChannel.bind(new InetSocketAddress(8080));
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }


        Worker worker = new Worker("worker-0");
        AtomicInteger index = new AtomicInteger();

        while (true) {
            boss.select();
            Iterator<SelectionKey> iterator = boss.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey nextSelectionKey = iterator.next();
                iterator.remove();
                log.debug("selectionKey: {}", nextSelectionKey);
                if (nextSelectionKey.isAcceptable()) {
                    //  ServerSocketChannel currentServerSocketChannel = (ServerSocketChannel) nextSelectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    workers[index.getAndIncrement() % workers.length].start(socketChannel);
                    worker.start(socketChannel);
                    log.info("after register");
                }
            }
        }

    }

    public static class Worker implements Runnable {
        private Selector selector;
        private volatile boolean start = false;
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue();
        private String name;
        private Thread thread;

        public Worker(String name) {
            this.name = name;
        }

        public void start(SocketChannel socketChannel) throws IOException {
            if (!start) {
                selector = Selector.open();
                thread = new Thread(this, name);
                thread.start();
                start = true;
            }

            queue.add(() -> {
                try {
                    socketChannel.register(selector, SelectionKey.OP_READ, null);
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });

            selector.wakeup(); // 唤醒select
        }

        @Override
        public void run() {
            while (true) {
                try {
                    selector.select();
                    Runnable task = queue.poll();
                    if (task != null) {
                        task.run();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    iterator.remove();

                    if (next.isReadable()) {
                        try {
                            SocketChannel socketChannel = (SocketChannel) next.channel(); // 拿到触发事件的channel
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            int read = socketChannel.read(buffer); // 如果是正常断开  read的返回值是 -1
                            if (read == -1) {
                                next.cancel();
                                log.info("客户端已主动断开");
                                continue;
                            }
                            Util.split(buffer);
                        } catch (IOException e) {
                            log.error(e.getMessage(), e);
                            // e.printStackTrace();
                            next.cancel();  // 客户端断开了  需要将 key从 selector 的key集合中删除
                        }
                    }
                }
            }

        }

    }

}
