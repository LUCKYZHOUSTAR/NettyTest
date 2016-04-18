/**     
 * @FileName: TcpClientHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:35:32   
 * @version V1.0     
 */
package Netty4.zhanbao.FixedLengthFrameDecoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: TcpClientHandler   
 * @Description: LineBaseFrameDecoder的工作原理是它依次遍历byteBuf中的可读字节，判断是否有“\n”
 * 或者"\r\n",如果有的话，就以此位置为结束标志，从可读索引 到结束位置区间的字节就组成了一行。它
 * 是以换行符为结束标志的解码器，支持携带结束标志或者不携带结束符的两种解码方式
 * 同时支持当行的最大长度。如果
 * 连续读取到最大长度后仍然没有发现换行符的话，就会跑出异常
 * 两者组成按照行切换的文本解码器
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
        // 循环发送100条消息
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String body = (String) msg;
        System.out.println("Now is:" + body + "; the counter is:" + ++counter);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)   
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("我已经读取完毕了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    

}
