package org.lwl.netty.chapter.channel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ScatterAndGatherTest {

    @Test
    public void test() {
        try {
            // 1.字节输入管道
            FileInputStream fis = new FileInputStream("data01.txt");
            FileChannel isChannel = fis.getChannel();
            // 2.字节输出管道
            FileOutputStream fos = new FileOutputStream("data03.txt");
            FileChannel osChannel = fos.getChannel();
            // 3.定义多个缓冲区做数据分散
            ByteBuffer buffer1 = ByteBuffer.allocate(3);
            ByteBuffer buffer2 = ByteBuffer.allocate(1024);
            ByteBuffer[] buffers = {buffer1, buffer2};
            // 4.从通道中读取数据分散到各个缓冲区
            isChannel.read(buffers);
            // 5.从每个缓冲区中查询是否有数据读取到
            for (ByteBuffer buffer : buffers) {
                // 切换到读数据模式
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, buffer.remaining()));
            }
            // 6.聚集写入到通道
            osChannel.write(buffers);
            isChannel.close();
            osChannel.close();
            System.out.println("复制完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
