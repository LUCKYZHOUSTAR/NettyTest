/**     
 * @FileName: ProtocolClientHandler.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:08:31   
 * @version V1.0     
 */
package Netty4.Protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**  
 * @ClassName: ProtocolClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:08:31     
 */
public class ProtocolClientHandler extends SimpleChannelInboundHandler<Object>{

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */  
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object obj) throws Exception {
        // TODO Auto-generated method stub
        Channel incoming=ctx.channel();
        System.out.println("Server--->Client"+incoming.remoteAddress()+obj.toString());
        
        if(obj instanceof  ProtocolMsg){
            ProtocolMsg msg=(ProtocolMsg) obj;
            System.out.println("Server-->Client"+incoming.remoteAddress()+msg.getBody());
        }
    }

}
