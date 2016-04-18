/**     
 * @FileName: ByeBuffer.java   
 * @Package:Netty4.buffer   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月7日 上午9:25:10   
 * @version V1.0     
 */
package Netty4.buffer;

import java.nio.ByteBuffer;

import javassist.expr.NewArray;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.ByteBufProcessor;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.util.ByteProcessor;

import org.junit.Test;

/**  
 * @ClassName: ByeBuffer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月7日 上午9:25:10     
 */
public class ByeBufferTest {

    @Test
    public void Test() throws Exception {
        String msg = "你好吗,我很好啊你怎么样啊";
        msg.getBytes();
        ByteBuf bytebuf = Unpooled.buffer(40);
     
        //        bytebuf.writeInt(3);
        //        System.out.println(bytebuf.readInt());
        bytebuf.writeBytes(msg.getBytes());
        //bytebuf的总容量
        System.out.println("总容量：" + bytebuf.capacity());
        //bytebuf的可读数目
        System.out.println("可读数目：" + bytebuf.readableBytes());
        //bytebuf剩余的可写数目
        System.out.println("可写空间:" + bytebuf.writableBytes());
        System.out.println("---------------读取数据后的一些变化操作-----------");

        System.out.println("------写入一些数据后的变化----------------");
        bytebuf.writeByte(3);
        bytebuf.writeBoolean(false);
        bytebuf.writeInt(45);
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());
        bytebuf.writeByte(3);
        bytebuf.writeBoolean(false);
        bytebuf.writeInt(45);
        byte[] req = new byte[bytebuf.readableBytes()];
        bytebuf.readBytes(req);
        System.out.println(new String(req));
        //开始读取字节
        System.out.println("---------开始读取字节-----------");
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());
        System.out.println("--------压缩读取的空间后----------");
        bytebuf.discardReadBytes();
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());
        System.out.println(bytebuf.readInt());
        System.out.println(bytebuf.readByte());
        //                System.out.println(bytebuf.readBoolean());
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());

        System.out.println("--------压缩读取的空间后----------");
        bytebuf.discardReadBytes();
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());

        System.out.println("--------调用clear清空操作----------");
        bytebuf.clear();
        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());
    }

    @Test
    public void testMark() {
        ByteBuf bytebuf = Unpooled.buffer(40);
        bytebuf.writeInt(3);
        bytebuf.writeInt(10);
        System.out.println("建立一个写的书签");
        bytebuf.markWriterIndex();
        bytebuf.writeInt(12);
        bytebuf.writeInt(24);
        System.out.println("建立书签操作");
        bytebuf.markReaderIndex();
        System.out.println("读取第一个值操作" + bytebuf.readInt());
        System.out.println("读取第二个值操作" + bytebuf.readInt());
        System.out.println("回滚到第一个读的书签操作");
        bytebuf.resetReaderIndex();
        System.out.println("读取第一个值操作" + bytebuf.readInt());
        System.out.println("重新回到写的书签操作");
        byte[] req = new byte[bytebuf.readableBytes()];
        bytebuf.readBytes(req);
        System.out.println(new String(req));
    }

    @Test
    public void queryByteBuf() {
        ByteBuf bytebuf = Unpooled.buffer(40);
        String msg = "我爱你像老鼠爱大米a,\n,你好吗";
        bytebuf.writeBytes(msg.getBytes());
        int result5 = bytebuf.forEachByte(new ByteProcessor() {
            //查找换行符的位置，但是不知道为什么需要！符号
            public boolean process(byte value) throws Exception {
                return value != 10;
            }
        });
        System.out.println(result5);
        System.out.println(msg.getBytes().toString());
        System.out.println(msg.getBytes().length);
        bytebuf.writeBytes(msg.getBytes());
        byte b = (byte) '中';
        System.out.println(b);

        System.out.println("总容量：" + bytebuf.capacity());
        System.out.println("可读数目：" + bytebuf.readableBytes());
        System.out.println("可写空间:" + bytebuf.writableBytes());

        //从当前bytebuf中定位首次出现value的位置，索引为readINdex，重点为writerindex
        System.out.println(bytebuf.bytesBefore(b));
        
        int result = bytebuf.indexOf(0, bytebuf.readableBytes(), b);
        //从当前bytebuf中定位首次出现value的位置，索引为readINdex，重点为readindex+length
        bytebuf.bytesBefore(2, b);
        //从当前bytebuf中定位首次出现value的位置，索引为index，重点为index+length，查询不到返回-1
        bytebuf.bytesBefore(2, 10, b);
        System.out.println(result);

        //遍历当前bytebuf可读字节数组，与byteProcessor设置的查询条件做对比，如果满足条件的话，则
        //返回位置索引，否则返回-1

        System.out.println(result5);
    }

    @Test
    public void test() {
        //char  byte 
        //-128 127
        //        String msg=new String("我是谁");
        String msg = "我是谁";
        System.out.println(msg.length());
        byte[] byeArray = msg.getBytes();
        for (byte c : byeArray) {
            System.out.println(c);
        }
        System.out.println("-------------");
        byte b = (byte) 'a';
        int s = (int) 'a';
        //-128 127
        System.out.println(s % 256);
        System.out.println(s);
        System.out.println(b);
    }

    public void TestCopyBuffer() {
        ByteBuf bytebuf = Unpooled.buffer(40);
        //复制出来的对象，与先前的byteBuf共享一个 缓冲区的内容，但是维护自己的读写索引，它的内容改变后，我的内容也会改变
        ByteBuf byteBuf2 = bytebuf.duplicate();
        //只是复制了内容，其他的都是单独维护操作
        ByteBuf byteBuf3 = bytebuf.copy();
        //返回当前的可读 子缓冲区，共享内容，但是读写索引，单独维护
        ByteBuf byteBuf4 = bytebuf.slice();
    }

    public void TestNioBuffer() {
        ByteBuf bytebuf = Unpooled.buffer(40);
        //两者共享一个缓冲区的引用，但是读写操作不会修改元bytebuf的索引
        ByteBuffer nioByteBuffer = bytebuf.nioBuffer();
       
    }

    /**   
     * @Title: testSetAndGet   
     * @Description: 
     * @param   随机读写操作,不支持动态的扩展，需要检查长度的限制
     * @return void  
     * @throws   
     */
    public void testSetAndGet() {
        
        ByteBuf bytebuf = Unpooled.buffer(40);
        bytebuf.getByte(3);
        bytebuf.setBoolean(3, false);
    }
    
    
    
    /**   
     * @Title: TestByteBufferHolder   
     * @Description: ByteBuf的容器，例如http协议的请求消息和应答消息都可以携带消息体
     * 因此就可以用改对象进行保证
     * @param   
     * @return void  
     * @throws   
     */
    public void TestByteBufferHolder(){
//        ByteBufHolder
//        ByteBufHolder byteBufHolder=
    }
    
    
    /**   
     * @Title: TestByteBufAllocator   
     * @Description: 字节缓冲区分配器：基于内存分配器和基于内存池的字节缓冲区和普通的字节缓冲区 分配器
     * @param   
     * @return void  
     * @throws   
     */
    public void TestByteBufAllocator(){
//        ByteBufAllocator
        ByteBufAllocator byteBufAllocator=new PooledByteBufAllocator();
        //缓冲区
        byteBufAllocator.buffer();
        //direct buffer
        byteBufAllocator.directBuffer();
        byteBufAllocator.heapBuffer();
        byteBufAllocator.compositeBuffer();
    }
    
    
    /**   
     * @Title: TestCompositeByteBuf   
     * @Description: 可以将多个byteBuf组装到一起，例如某个协议POJO包含
     * 两个部分，消息头和消息体，他们都是byteBuf对象，当需要对消息进行整合
     * 编码的时候，需要整合，因此可以再用该buf
     * 里面定义了一个Component类型的集合，该集合就是byteBUf的包装，可以采取下面的操纵来添加byteBuf对象
     * @param   
     * @return void  
     * @throws   
     */
    public void TestCompositeByteBuf(){
        //buf的缓冲池操作
        ByteBufAllocator byteBufAllocator=new PooledByteBufAllocator();
        
        byteBufAllocator.compositeBuffer().addComponent(Unpooled.buffer(40));
    }
    
    
    
    /**   
     * @Title: TestByteBufUtil   
     * @Description: 
     * @param   工具类用来操作byteBuf对象
     * @return void  
     * @throws   
     */
    public void TestByteBufUtil(){
//        ByteBufUtil
    }
    
    
    
    
    /**   
     * @Title: TestGetByte   
     * @Description: 顺序读写操作，但是需要指定读写的位置
     * @param   
     * @return void  
     * @throws   
     */
    @Test
    public void TestGetByte(){
        ByteBuf bytebuf = Unpooled.buffer(40);
        String preLen=  String.format("%08d", 8);
        bytebuf.writeBytes(preLen.getBytes());
        byte[] bytebuffer=new byte[8];
        bytebuf.getBytes(1, bytebuffer);
        System.out.println(new String(bytebuffer));
        
    }
}
