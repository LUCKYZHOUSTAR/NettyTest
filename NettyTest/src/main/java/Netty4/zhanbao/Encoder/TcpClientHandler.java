/**     
 * @FileName: TcpClientHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:35:32   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.ByteBuffer;

/**  
 * @ClassName: TcpClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:35:32     
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    private int    counter;
    private byte[] req;

    public TcpClientHandler() {
        req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)   
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String msg="你好吗，我很好啊";
        //需要输入8个字节代表长度
        byte[] tempData = new byte[8 + msg.getBytes().length];
        //此处是关键的地方
        String len = String.format("%8d", new Object[] { msg.getBytes().length });
        ByteBuffer buffer = ByteBuffer.wrap(tempData);
        tempData = len.getBytes();
        buffer.put(tempData);
        buffer.put(msg.getBytes());
        buffer.compact();
        ctx.writeAndFlush(buffer.array());
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Now is:" + body + "; the counter is:" + ++counter);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)   
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

}
