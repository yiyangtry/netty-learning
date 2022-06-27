package org.lwl.netty.chapter.nio.channel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ReadTest {

    @Test
    public void read() {
        try {
            // 1.定义一个文件字节输入流与源文件接通
            FileInputStream fis = new FileInputStream("data01.txt");
            // 2.需要得到文件输入流的文件通道
            FileChannel channel = fis.getChannel();
            // 3.定义一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 4.读取数据到缓冲区
            channel.read(buffer);
            buffer.flip();
            // 5.读取出缓冲区中的数据并输出
            String rs = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(rs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
