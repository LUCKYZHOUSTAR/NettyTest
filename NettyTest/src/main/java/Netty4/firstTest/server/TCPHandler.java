/**     
 * @FileName: TCPHandler.java   
 * @Package:Netty4.firstTest.server   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 下午4:33:44   
 * @version V1.0     
 */
package Netty4.firstTest.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**  
 * @ClassName: TCPHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 下午4:33:44     
 */
public class TCPHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //        byte[] msgBody = new byte[msg.limit()];
        //        msg.get(msgBody);
        byte[] resultData = new byte[msg.readableBytes()];
        msg.readBytes(resultData);
        System.out.println("服务端收到应答" + new String(resultData));
        String msgResult = "我已经收到应答了";
        ByteBuf resp = Unpooled.copiedBuffer(msgResult.getBytes());
        ctx.write(resp);
    }

}
