/**     
 * @FileName: ProtocolEncoder.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 上午10:48:40   
 * @version V1.0     
 */
package Netty4.Protocol;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**  
 * @ClassName: ProtocolEncoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 上午10:48:40     
 */
public class ProtocolEncoder extends MessageToByteEncoder<ProtocolMsg> {

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @param out
     * @throws Exception   
     * @see io.netty.handler.codec.MessageToByteEncoder#encode(io.netty.channel.ChannelHandlerContext, java.lang.Object, io.netty.buffer.ByteBuf)   
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, ProtocolMsg msg, ByteBuf out) throws Exception {
        if (msg == null || msg.getProtocolHeader() == null) {
            throw new Exception("the encoder message is null");
        }

        ProtocolHeader header = msg.getProtocolHeader();
        String body = msg.getBody();
        byte[] bodyBytes = body.getBytes(Charset.forName("utf-8"));
        int bodySize = bodyBytes.length;

        out.writeByte(header.getMagic());
        out.writeByte(header.getMsgType());
        out.writeShort(header.getReserve());
        out.writeShort(header.getSn());
        out.writeInt(bodySize);
        out.writeBytes(bodyBytes);

    }

}
