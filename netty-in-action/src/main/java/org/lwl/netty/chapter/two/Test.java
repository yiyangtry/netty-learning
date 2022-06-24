package org.lwl.netty.chapter.two;

import io.netty.channel.DefaultEventLoopGroup;
import io.netty.util.concurrent.EventExecutor;

public class Test {

    public static void main(String[] args) {
        DefaultEventLoopGroup group = new DefaultEventLoopGroup(2);
        for (EventExecutor eventLoop : group) {
            System.out.println(eventLoop);
        }
    }
}
