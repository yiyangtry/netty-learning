package org.lwl.netty.chapter.two.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @author thinking_fioa
 * @createTime 2018/5/16
 * @description 代码清单 2-2，服务端引导器
 */


public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args)
            throws Exception {

        int port = 9080;
        new EchoServer(port).start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        EchoServerHandler serverHandler = new EchoServerHandler();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    .channel(NioServerSocketChannel.class) // 指定所使用的 NIO 传输 Channel
                    .localAddress(new InetSocketAddress(port)) // 使用指定的端口设置套接字地址
                    // 添加一个EchoServerHandler到子Channel的 ChannelPipeline
                    // 它的作用是待客户端SocketChannel建立连接后，执行initChannel以便添加更多的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(serverHandler);
                        }
                    });
            // 异步绑定服务器 调用 sync()方法阻阻塞等待直到绑定完成
            // 实际项目中，b.bind().sync()可以省略
            ChannelFuture f = b.bind().sync();
            System.out.println(EchoServer.class.getName() +
                    " started and listening for connections on " + f.channel().localAddress());

            // 获取 Channel 的CloseFuture，并且阻塞当前线 程直到它完成
            // 实际项目中，f.channel().closeFuture().sync()可以省略
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }
}
