/**     
 * @FileName: TcpServerHandlerr.java   
 * @Package:Netty4.firstTest.fengzhuang   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 下午12:23:25   
 * @version V1.0     
 */
package Netty4.firstTest.fengzhuang;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: TcpServerHandlerr   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 下午12:23:25     
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receiveMsg = (ByteBuf) msg;
        byte[] receData = new byte[receiveMsg.readableBytes()];
        receiveMsg.readBytes(receData);
        System.out.println("服务端读取到消息" + new String(receData));
        //释放资源
        receiveMsg.release();
        String response = "yes,server is ok";
        //加入长度的字段
        String preLen = String.format("%08d", response.getBytes().length);
        ByteBuf encoded = Unpooled.buffer(8 + response.getBytes().length);
        encoded.writeBytes(preLen.getBytes());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded);
        ctx.close();

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (ctx.channel().isActive()) {
            ctx.writeAndFlush(
                "ERR: " + cause.getClass().getSimpleName() + ": " + cause.getMessage() + '\n')
                .addListener(ChannelFutureListener.CLOSE);
        }
    }

}
