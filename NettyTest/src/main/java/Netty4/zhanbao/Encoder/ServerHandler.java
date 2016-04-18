/**     
 * @FileName: ServerHandler.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午10:52:18   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

import java.nio.ByteBuffer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**  
 * @ClassName: ServerHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 上午10:52:18     
 */
public class ServerHandler extends SimpleChannelInboundHandler<ByteBuffer> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuffer msg) throws Exception {
        byte[] tempData = new byte[msg.capacity()];
        msg.get(tempData);
        System.out.println("服务端接受到消息"+new String(tempData));
        ctx.writeAndFlush(msg);
    }

}
