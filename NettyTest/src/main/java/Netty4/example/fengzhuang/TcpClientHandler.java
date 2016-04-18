/**     
 * @FileName: TcpClientHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:35:32   
 * @version V1.0     
 */
package Netty4.example.fengzhuang;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: TcpClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:35:32     
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter{

    
    private final ByteBuf firstMessage;
    private String result;
    
    public TcpClientHandler(){
        byte[] req="QUERY TIME ORDER".getBytes();
        firstMessage=Unpooled.buffer(req.length);
        firstMessage.writeBytes(req);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(firstMessage);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */  
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body=new String(req,"UTF-8");
        this.result=body;
        System.out.println(body);
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

    /**   
     * @return result   
     */
    public String getResult() {
        return result;
    }

    /**     
     * @param result the result to set     
     */
    public void setResult(String result) {
        this.result = result;
    }
    
    
    
    
}
