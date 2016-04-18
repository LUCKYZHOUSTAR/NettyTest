/**     
 * @FileName: TcpServerHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:47:19   
 * @version V1.0     
 */
package Netty4.example.fengzhuang;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**  
 * @ClassName: TcpServerHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:47:19     
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter{
    
    
    
    
    /*建立连接时的操作*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      
        
    }

    /* (non-Javadoc)   
     * @param ctx  读取服务器时的操作
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
        System.out.println(body);
        ByteBuf resp=Unpooled.copiedBuffer("你好吗".getBytes());
        ctx.write(resp);
    }
    

    
    
    
    /* (non-Javadoc)   
     * @param ctx  读完时的操作
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //当服务端关闭时，仍会走该方法
        ctx.flush();
    }




    /* (non-Javadoc)   
     * @param ctx
     * @param cause
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)   
     */  
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        ctx.close();
    }


    
    
}
