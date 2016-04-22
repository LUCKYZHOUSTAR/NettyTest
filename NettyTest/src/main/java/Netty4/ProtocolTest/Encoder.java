/**     
 * @FileName: Encoder.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午1:34:12   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: Encoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午1:34:12     
 */
public class Encoder extends MessageToByteEncoder<RemotingCommand> {
    private static final Logger log = LoggerFactory.getLogger(Encoder.class);

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception   
     * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)   
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, RemotingCommand msg, ByteBuf out)
                                                                                      throws Exception {

        try {
            int length = 0;
            if (msg.getBody() != null) {
                length += msg.getBody().length;
            }

            ByteBuffer result = ByteBuffer.allocate(8 + length);
            String preLen = String.format("%08d", length);
            result.put(preLen.getBytes());
            if (msg.getBody() != null) {
                result.put(msg.getBody());
            }
            result.flip();
            out.writeBytes(result);
        } catch (Exception e) {
            log.error("encode exception, " + ctx.channel().remoteAddress(), e);
            if (msg != null) {
                log.error(msg.toString());
            }
            ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
        }
    }

}
