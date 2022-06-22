package com.asm;

import com.asm.me.legault.hdlc.CompactBitSet;
import com.asm.me.legault.hdlc.Frame;
import com.asm.me.legault.hdlc.IFrame;
import com.asm.util.ConvertCode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;

import java.util.Arrays;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class TestHdlc {

    static final byte[] initByte = {(byte)0x7e, (byte)0x00, (byte)0xca, (byte)0x00, (byte)0x01, (byte)0x00, (byte)0x00, (byte)0x30, (byte)0x30, (byte)0x32, (byte)0x30,
            (byte)0x32, (byte)0x30, (byte)0x32, (byte)0x31, (byte)0x31, (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x34, (byte)0x30, (byte)0x34,
            (byte)0x2e, (byte)0x32, (byte)0x34, (byte)0x30, (byte)0x31, (byte)0x3a, (byte)0xe5, (byte)0xb7, (byte)0xa5, (byte)0xe4, (byte)0xbd, (byte)0x9c,
            (byte)0xe4, (byte)0xbd, (byte)0x8d, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b, (byte)0x30, (byte)0x32,
            (byte)0x3a, (byte)0xe7, (byte)0x94, (byte)0xb5, (byte)0xe6, (byte)0x9c, (byte)0xba, (byte)0xe4, (byte)0xbd, (byte)0x8d, (byte)0xe6, (byte)0x8e,
            (byte)0xa7, (byte)0xe5, (byte)0x88, (byte)0xb6, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b, (byte)0x30,
            (byte)0x33, (byte)0x3a, (byte)0xe9, (byte)0x97, (byte)0xb8, (byte)0xe9, (byte)0x81, (byte)0x93, (byte)0xe4, (byte)0xbd, (byte)0x8d, (byte)0xe6,
            (byte)0x8e, (byte)0xa7, (byte)0xe5, (byte)0x88, (byte)0xb6, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b,
            (byte)0x30, (byte)0x34, (byte)0x3a, (byte)0xe6, (byte)0x95, (byte)0xb0, (byte)0xe6, (byte)0x8d, (byte)0xae, (byte)0xe9, (byte)0x87, (byte)0x87,
            (byte)0xe9, (byte)0x9b, (byte)0x86, (byte)0x49, (byte)0x4f, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b,
            (byte)0x30, (byte)0x35, (byte)0x3a, (byte)0xe5, (byte)0xb7, (byte)0xa5, (byte)0xe4, (byte)0xbd, (byte)0x9c, (byte)0xe4, (byte)0xbd, (byte)0x8d,
            (byte)0xe8, (byte)0x87, (byte)0xaa, (byte)0xe6, (byte)0xa3, (byte)0x80, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab,
            (byte)0x3b, (byte)0x30, (byte)0x36, (byte)0x3a, (byte)0xe5, (byte)0xb7, (byte)0xa5, (byte)0xe4, (byte)0xbd, (byte)0x9c, (byte)0xe4, (byte)0xbd,
            (byte)0x8d, (byte)0x42, (byte)0x79, (byte)0x70, (byte)0x61, (byte)0x73, (byte)0x73, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88,
            (byte)0xab, (byte)0x3b, (byte)0x30, (byte)0x37, (byte)0x3a, (byte)0x55, (byte)0x56, (byte)0xe8, (byte)0xae, (byte)0xbe, (byte)0xe5, (byte)0xa4,
            (byte)0x87, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b, (byte)0x30, (byte)0x46, (byte)0x3a, (byte)0xe6,
            (byte)0x9c, (byte)0xaa, (byte)0xe7, (byte)0x9f, (byte)0xa5, (byte)0xe7, (byte)0xb1, (byte)0xbb, (byte)0xe5, (byte)0x88, (byte)0xab, (byte)0x3b,
            (byte)0xd6, (byte)0x2f, 0x7e};

    static final byte[] regByte = {(byte)0x7e, (byte)0x00, (byte)0x14, (byte)0x00, (byte)0x02, (byte)0x00, (byte)0x00, (byte)0x30, (byte)0x30, (byte)0x32,
            (byte)0x30, (byte)0x32, (byte)0x30, (byte)0x32, (byte)0x31, (byte)0x31, (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x30, (byte)0x34, (byte)0x8a,
            (byte)0x4d, (byte)0x7e};
    // 封装方法
    @Test
    public void testCovertCode()
    {
        final byte[] bytes = {0x34,0x35,0x36};
        final String s = ConvertCode.bytes2HexString(bytes);
        System.out.println(s);  // : 343536

        String s1 = "7E000034353643E17E";  // : 【 十六进制 】
        final byte[] hexBytes = ConvertCode.hexString2Bytes(s1);
        System.out.println(Arrays.toString(hexBytes)); // : [126, 0, 0, 52, 53, 54, 67, -31, 126]  【十进制】

    }

    // 使用框架   【编码】
    @Test
    public void t1()
    {
//        final byte[] bytes = {0x34,0x35,0x36};

        final byte[] bytes = ConvertCode.hexString2Bytes("000300010270d0");

        final IFrame iFrame = new IFrame(CompactBitSet.fromBytes(bytes));

        final byte commandType = iFrame.getCommandType();
        System.out.println(commandType);

        System.out.println("iFrame.toString()  = " + iFrame.toString());// : iFrame.toString()  = 7E 00 00 34 35 36 43 E1 7E     【 十六进制 】
        System.out.println("iFrame.toHexString()  = " + iFrame.toHexString());// : iFrame.toHexString()  = 7E000034353643E17E    【 十六进制 】

        final byte[] bytes1 = iFrame.toByteArray();
        System.out.println("bytes1  = " + Arrays.toString(    bytes1   ));// : bytes1  = [126, 0, 0, 52, 53, 54, 67, -31, 126]   【 十进制 】

    }

    // 不使用框架
    @Test
    public void t2()
    {
        System.out.println(regByte.length);
        final CompactBitSet c = CompactBitSet.fromBytes(TestHdlc.regByte);
        System.out.println("十六进制1  = " + ConvertCode.bytes2HexString(TestHdlc.regByte));     // : 十六进制1  = 343536
        System.out.println("十六进制2  = " + c.toHexString());                                   // : 十六进制2  = 343536
        System.out.println("十进制显示  = " + Arrays.toString(    c.toByteArray()   ));          // : 十进制显示  = [52, 53, 54]

    }

    // 使用框架 + 验证    【 入站 解码 (带验证数据)】
    @Test
    public void t3()
    {
        final Frame frame = Frame.fromByteArray(CompactBitSet.fromBytes(TestHdlc.initByte));

        final byte[] bytes1 = frame.toByteArray();
        System.out.println("bytes1  = " + Arrays.toString(    bytes1   )); // : com.asm.me.legault.hdlc.InvalidFrameException: Invalid start flag

    }

    // 使用框架 + 验证
    @Test
    public void t31()
    {
        final CompactBitSet compactBitSet = CompactBitSet.fromBytes(TestHdlc.initByte);
        final String s = compactBitSet.toHexString();
        System.out.println(s);


    }





    @Test
    public void anyTest()
    {
/*        int i = 0x01;
        String s = Integer.toBinaryString(i);
        System.out.println(s);
        i = i >> 1;
        System.out.println(i);*/

        final CompactBitSet cbs = CompactBitSet.fromBytes(regByte);

        System.out.println(cbs.toHexString());

        final int numberOfBits = cbs.getNumberOfBits();
        System.out.println(numberOfBits);
    }

    @Test
    public void andTest2()
    {
        final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
        buf.writeBytes(new byte[]{'a','b','c','d','e','f','g','h','i','j'});

        log(buf);
        /*
        read index:0 write index:10 capacity:10
         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 61 62 63 64 65 66 67 68 69 6a                   |abcdefghij      |
+--------+-------------------------------------------------+----------------+
         */
        System.out.println("ByteBuf 可读取长度 = " + buf.readableBytes());

        final ByteBuf buf2 = ByteBufAllocator.DEFAULT.buffer(10);
        buf2.writeBytes(buf);
        log(buf2);


        final ByteBuf f2 = buf.slice(0, 5);
        f2.retain(); // 对切片=>先引用计数+1
        log(f2);
        buf.release();
        f2.release();

    }

    private static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE); // Import static constant... netty的
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
