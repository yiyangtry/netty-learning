package org.lwl.netty.chapter.channel;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author yiyang
 * @date 2022/6/23 19:50
 */
public class TransferFromAndTransferToTest {
    @Test
    public void test02() {
        try {
            // 1.字节输入管道
            FileInputStream fis = new FileInputStream("data01.txt");
            FileChannel isChannel = fis.getChannel();
            // 2.字节输出管道
            FileOutputStream fos = new FileOutputStream("data04.txt");
            FileChannel osChannel = fos.getChannel();
            // 3.复制数据
            osChannel.transferFrom(isChannel, isChannel.position(), isChannel.size());
            isChannel.close();
            osChannel.close();
            System.out.println("复制完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test03() {
        try {
            // 1.字节输入管道
            FileInputStream fis = new FileInputStream("data01.txt");
            FileChannel isChannel = fis.getChannel();
            // 2.字节输出管道
            FileOutputStream fos = new FileOutputStream("data05.txt");
            FileChannel osChannel = fos.getChannel();
            // 3.复制数据
            isChannel.transferTo(isChannel.position(), isChannel.size(), osChannel);
            isChannel.close();
            osChannel.close();
            System.out.println("复制完成!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
