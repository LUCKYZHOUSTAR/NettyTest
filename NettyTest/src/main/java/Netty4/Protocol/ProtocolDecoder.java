/**     
 * @FileName: ProtocolDecoder.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 上午10:04:48   
 * @version V1.0     
 */
package Netty4.Protocol;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**  
 * @ClassName: ProtocolDecoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 上午10:04:48     
 */
public class ProtocolDecoder extends LengthFieldBasedFrameDecoder {

    private static final int HEADER_SIZE = 10;

    private byte             magic;           //帧数
    private byte             msgType;         //消息类型
    private short            reserve;         //保留字
    private short            sn;              //序列号
    private int              len;             //长度

    /**
     * @param maxFrameLength
     * @param lengthFieldOffset
     * @param lengthFieldLength
     * @param lengthAdjustment
     * @param initialBytesToStrip
     */
    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                           int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
            initialBytesToStrip);
    }

    /**
     * @param maxFrameLength
     * @param lengthFieldOffset
     * @param lengthFieldLength
     * @param lengthAdjustment
     * @param initialBytesToStrip
     * @param failFast
     */
    public ProtocolDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength,
                           int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,
            initialBytesToStrip, failFast);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param in
     * @return
     * @throws Exception   
     * @see io.netty.handler.codec.LengthFieldBasedFrameDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf)   
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in2) throws Exception {
        // TODO Auto-generated method stub

        ByteBuf in = (ByteBuf) super.decode(ctx, in2);
        if (in == null) {
            return null;
        }

        if (in.readableBytes() < HEADER_SIZE) {
            return null;
        }

        ByteBuf buf = in.readBytes(len);
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        ProtocolMsg msg=new ProtocolMsg();
        ProtocolHeader protocolHeader=new ProtocolHeader();
        msg.setBody(body);
        msg.setProtocolHeader(protocolHeader);
        return msg;
    }

}
