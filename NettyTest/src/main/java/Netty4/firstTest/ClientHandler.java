/**     
 * @FileName: ClientHandler.java   
 * @Package:Netty4.firstTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月5日 下午5:07:04   
 * @version V1.0     
 */
package Netty4.firstTest;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**  
 * @ClassName: ClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月5日 下午5:07:04     
 */
public class ClientHandler extends ChannelHandlerAdapter{

    /* (non-Javadoc)   
     * @param ctx
     * @param cause
     * @throws Exception   
     * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)   
     */  
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, cause);
    }

    /* (non-Javadoc)   
     * @param channelhandlercontext
     * @throws Exception   
     * @see io.netty.channel.ChannelHandlerAdapter#handlerAdded(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void handlerAdded(ChannelHandlerContext channelhandlercontext) throws Exception {
        
        
        
    }

    /* (non-Javadoc)   
     * @param channelhandlercontext
     * @throws Exception   
     * @see io.netty.channel.ChannelHandlerAdapter#handlerRemoved(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void handlerRemoved(ChannelHandlerContext channelhandlercontext) throws Exception {
        
        
        
    }

}
