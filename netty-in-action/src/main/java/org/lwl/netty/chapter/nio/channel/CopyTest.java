package org.lwl.netty.chapter.nio.channel;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyTest {

    @Test
    public void copy() {
        try {
            // 源文件
            File srcFile = new File("data01.txt");
            // 目标文件
            File destFile = new File("data02.txt");
            // 得到字节输入流
            FileInputStream fis = new FileInputStream(srcFile);
            // 得到字节输出流
            FileOutputStream fos = new FileOutputStream(destFile);
            // 得到文件通道
            FileChannel isChannel = fis.getChannel();
            FileChannel osChannel = fos.getChannel();
            // 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                // 必须先清空缓冲区再写入数据到缓冲区
                buffer.clear();
                // 开始读取一次数据
                int flag = isChannel.read(buffer);
                if (flag == -1) {
                    break;
                }
                // 已经读取了数据,把缓冲区的模式切换成可读模式
                buffer.flip();
                // 把数据写出
                osChannel.write(buffer);
            }
            isChannel.close();
            osChannel.close();
            System.out.println("复制完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
