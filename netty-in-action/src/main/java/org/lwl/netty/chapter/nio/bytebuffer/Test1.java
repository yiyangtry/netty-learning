package org.lwl.netty.chapter.nio.bytebuffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

public class Test1 {

    @Test
    public void test01() {
        // 1.分配一个缓冲区,容量设置成10
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.limit()); // 10
        System.out.println(buffer.capacity()); // 10
        System.out.println("--------------------");

        // 2.put()往缓冲区中添加数据
        String name = "hello";
        buffer.put(name.getBytes());
        System.out.println(buffer.position()); // 5
        System.out.println(buffer.limit()); // 10
        System.out.println(buffer.capacity()); // 10
        System.out.println("--------------------");

        // 3.flip()为将缓冲区的界限设置为当前位置,并将当前位置重置为0 可读模式
        buffer.flip();
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.limit()); // 5
        System.out.println(buffer.capacity()); // 10
        System.out.println("--------------------");

        // 4.get()数据的读取
        char ch = (char) buffer.get();
        System.out.println(ch);
        System.out.println(buffer.position()); // 1
        System.out.println(buffer.limit()); // 5
        System.out.println(buffer.capacity()); // 10
        System.out.println("--------------------");
    }

    @Test
    public void test02() {
        // 1.分配一个缓冲区,容量设置成10 put()往缓冲区中添加数据
        ByteBuffer buffer = ByteBuffer.allocate(10);
        String name = "hello";
        buffer.put(name.getBytes());
        System.out.println(buffer.position()); // 5
        System.out.println(buffer.limit()); // 10
        System.out.println(buffer.capacity()); // 10
        System.out.println("--------------------");

        // 2.clear()清除缓冲区中的数据 并没有真正清除数据,只是让position的位置恢复到初始位置,后续添加数据的时候才会覆盖每个位置的数据
        buffer.clear();
        System.out.println(buffer.position()); // 0
        System.out.println(buffer.limit()); // 10
        System.out.println(buffer.capacity()); // 10
        System.out.println((char) buffer.get()); // h
        System.out.println("--------------------");

        // 3.定义一个缓冲区
        ByteBuffer buf = ByteBuffer.allocate(10);
        String n = "hello";
        buf.put(n.getBytes());
        buf.flip();
        // 读取数据
        byte[] b = new byte[2];
        buf.get(b);
        System.out.println(new String(b));
        System.out.println(buf.position()); // 2
        System.out.println(buf.limit()); // 5
        System.out.println(buf.capacity()); // 10
        System.out.println("--------------------");

        buf.mark(); // 标记此刻这个位置 2

        byte[] b2 = new byte[3];
        buf.get(b2);
        System.out.println(new String(b2));
        System.out.println(buf.position()); // 5
        System.out.println(buf.limit()); // 5
        System.out.println(buf.capacity()); // 10
        System.out.println("--------------------");

        buf.reset(); // 回到标记位置
        if (buf.hasRemaining()) {
            System.out.println(buf.remaining()); // 3
            System.out.println(buf.position()); // 3
        }
    }
}
