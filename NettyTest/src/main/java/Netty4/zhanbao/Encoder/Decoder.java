/**     
 * @FileName: Decoder.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午10:27:50   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: Decoder   
 * @Description: 把字节转换成对象来操作
 * @author: LUCKY  
 * @date:2016年4月14日 上午10:27:50     
 */
public class Decoder extends LengthFieldBasedFrameDecoder {
    private static final Logger log              = LoggerFactory.getLogger(Decoder.class);
    private static final int    FRAME_MAX_LENGTH = Integer.parseInt(System.getProperty(
                                                     "com.jdb.remoting.frameMaxLength", "8388608"));

    public Decoder() {
        super(FRAME_MAX_LENGTH, 0, 8, 0, 8);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param in
     * @return
     * @throws Exception   
     * @see io.netty.handler.codec.LengthFieldBasedFrameDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf)   
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        // TODO Auto-generated method stub
        ByteBuf frame = null;
        try {
            //得到去除消息长度的 消息体
            frame = (ByteBuf) super.decode(ctx, in);
            if (frame == null) {
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();
            int len = byteBuffer.capacity();
            byte[] bodyData = new byte[len];
            byteBuffer.get(bodyData);
            return byteBuffer;
        } catch (Exception e) {
            log.error("decode exception, " + ctx.channel().metadata().toString(), e);
            ctx.close();
        } finally {
            //最后释放channel
            if (null != frame) {
                frame.release();
            }
        }

        return null;

    }

    //需要获得自定义的消息的长度
    @SuppressWarnings("deprecation")
    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        byte[] lendData = new byte[8];
        //一个字节8位的长度值
        buf.getBytes(0, lendData);
        int frameLength = Integer.parseInt(new String(lendData));
        System.out.println("字节的长度为"+frameLength);
        return frameLength;
    }

}
