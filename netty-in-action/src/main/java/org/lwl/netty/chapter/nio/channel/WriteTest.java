package org.lwl.netty.chapter.nio.channel;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class WriteTest {
    @Test
    public void write() {
        try {
            // 1.字节输出流通向目标文件
            FileOutputStream fos = new FileOutputStream("data01.txt");
            // 2.得到字节输出流对应的通道Channel
            FileChannel channel = fos.getChannel();
            // 3.分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("Hello World!".getBytes());
            // 4.把缓冲区切换成写出模式
            buffer.flip();
            channel.write(buffer);
            channel.close();
            System.out.println("写数据到文件中!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
