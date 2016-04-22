/**     
 * @FileName: Decoder.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午1:22:49   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: Decoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午1:22:49     
 */
public class Decoder extends LengthFieldBasedFrameDecoder {
    private static final Logger log              = LoggerFactory.getLogger(Decoder.class);
    private static final int    FRAME_MAX_LENGTH = Integer.parseInt(System.getProperty(
                                                     "remoting.frameMaxLength", "8388608"));

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
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (frame == null) {
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();
            int len = byteBuffer.capacity();
            byte[] bodyData = new byte[len];
            byteBuffer.get(bodyData);
            RemotingCommand cmd = RemotingCommand.createRequestCommand();
            cmd.setBody(bodyData);
            return cmd;
        } catch (Exception e) {
            log.error("decode exception, " + ctx.channel().remoteAddress(), e);
            ctx.channel().close().addListener(new ChannelFutureListener() {

                public void operationComplete(ChannelFuture future) throws Exception {
                    // TODO Auto-generated method stub
                    log.info("closeChannel: close the connection to remote address[{}] result: {}",
                        future.channel().remoteAddress(), future.isSuccess());
                }
            });
        } finally {
            if (null != frame) {
                frame.release();
            }
        }
        return null;

    }

    /* (non-Javadoc)   
     * @param buf
     * @param offset
     * @param length
     * @param order
     * @return   
     * @see io.netty.handler.codec.LengthFieldBasedFrameDecoder#getUnadjustedFrameLength(io.netty.buffer.ByteBuf, int, int, java.nio.ByteOrder)   
     */
    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        byte[] lengdata = new byte[8];
        buf.getBytes(0, lengdata);
        int frameLength = Integer.parseInt(new String(lengdata));
        return frameLength;
    }

}
