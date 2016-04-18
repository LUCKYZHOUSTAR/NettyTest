/**     
 * @FileName: Decoder.java   
 * @Package:Netty4.firstTest.server   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 下午4:24:34   
 * @version V1.0     
 */
package Netty4.firstTest.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**  
 * @ClassName: Decoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 下午4:24:34     
 */
public class Decoder extends LengthFieldBasedFrameDecoder {

    private static int DATA_LENGTH = 8388608;

    public Decoder() {
        
        super(DATA_LENGTH, 0, 8, 0, 8);
    }

//    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf buf = null;
        try {
            buf = (ByteBuf) super.decode(ctx, in);
            ByteBuffer nioBuffer = buf.nioBuffer();
            int len = nioBuffer.capacity();
            byte[] bodyData = new byte[len];
            nioBuffer.get(bodyData);
            return nioBuffer;
        } catch (Exception e) {
            ctx.close();
        } finally {
            if (buf != null) {
                buf.release();
            }
        }

        return null;

    }

    /*该方法返回的是消息头的长度，需要自定义，具体看LengthFieldBasedFrameDecoder源码*/
    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        int msgLength;
        byte[] tempData = new byte[8];
        //buf的随机读操作，readindex指针为0
        buf.getBytes(0, tempData);
        msgLength = Integer.parseInt(new String(tempData));
        return msgLength;
    }

}
