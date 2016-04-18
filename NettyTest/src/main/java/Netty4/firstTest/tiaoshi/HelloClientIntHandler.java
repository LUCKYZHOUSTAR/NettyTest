/**     
 * @FileName: HelloClientIntHandler.java   
 * @Package:Netty4.firstTest.tongbu   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午10:46:33   
 * @version V1.0     
 */
package Netty4.firstTest.tiaoshi;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: HelloClientIntHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午10:46:33     
 */
//InboundHandler类型
public class HelloClientIntHandler extends ChannelInboundHandlerAdapter {

    public StringBuffer message;
    public String       sendData;

    public HelloClientIntHandler(StringBuffer message,String sendData) {
        this.message = message;
        this.sendData=sendData;
    }

    // 连接成功后，向server发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf encoded = ctx.alloc().buffer(sendData.getBytes().length);
        encoded.writeBytes(sendData.getBytes());
        ctx.write(encoded);
        ctx.flush();
    }

    // 接收server端的消息，并打印出来
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        message.append(new String(result1));
        result.release();
    }
}
