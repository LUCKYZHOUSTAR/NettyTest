/**     
 * @FileName: HelloServerInHandler.java   
 * @Package:Netty4.firstTest.tongbu   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午10:45:31   
 * @version V1.0     
 */
package Netty4.firstTest.tiaoshi;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: HelloServerInHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午10:45:31     
 */
//该handler是InboundHandler类型
public class HelloServerInHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        // msg中存储的是ByteBuf类型的数据，把数据读取到byte[]中
        result.readBytes(result1);
        String resultStr = new String(result1);
        // 接收并打印客户端的信息
        System.out.println("Client said:" + resultStr);
        // 释放资源，这行很关键
        result.release();
        // 向客户端发送消息
        String response = "我已经收到消息了";
        // 在当前场景下，发送的数据必须转换成ByteBuf数组
        ByteBuf encoded = ctx.alloc().buffer(4 * response.length());
        encoded.writeBytes(response.getBytes());
        ctx.writeAndFlush(encoded);
        ctx.close();
    }

}