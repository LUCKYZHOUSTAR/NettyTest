/**     
 * @FileName: Encoder.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午9:57:43   
 * @version V1.0     
 */
package Netty4.firstTest.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: Encoder   
 * @Description: decode()方法是唯一的抽象方法需要实现
 * 。从而让每一个入站消息的解码器,让把消息编码成另外一种格式
 * 解码消息然后通过ChannelInboundHandlerin到达 ChannelPipeline。
 * @author: LUCKY  
 * @date:2016年4月14日 上午9:57:43     
 */

//把对象转化为字节，通过btebuf.write即可
public class Encoder extends MessageToByteEncoder<ByteBuf> {
    private static final Logger log = LoggerFactory.getLogger(Encoder.class);

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception   
     * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)   
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
        try {
            ByteBuffer result = ByteBuffer.allocate(8 + msg.readableBytes());
            String preLen = String.format("%08d", msg.readableBytes());
            result.put(preLen.getBytes());
            byte[] tempData=new byte[msg.readableBytes()];
            msg.readBytes(tempData);
            if (msg != null) {
                result.put(tempData);
            }
            //开始读取的操作
            result.flip();
            out.writeBytes(result.array());
            
        } catch (Exception e) {
            log.error("decode exception, " + ctx.channel().metadata().toString(), e);
            ctx.close();
        }

    }
}
