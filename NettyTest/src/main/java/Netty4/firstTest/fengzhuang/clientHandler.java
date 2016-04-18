/**     
 * @FileName: clientHandler.java   
 * @Package:Netty4.firstTest.fengzhuang   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午11:04:54   
 * @version V1.0     
 */
package Netty4.firstTest.fengzhuang;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**  
 * @ClassName: clientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午11:04:54     
 */
public class clientHandler extends ChannelInboundHandlerAdapter {

    private StringBuffer msgResult;
    private String       sendData;

    public clientHandler(StringBuffer msgResult, String sendData) {
        this.msgResult = msgResult;
        this.sendData = sendData;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //加入长度的字段
        String preLen = String.format("%08d", sendData.getBytes().length);
        ByteBuf byteBuf = Unpooled.buffer(8 + sendData.getBytes().length);
        byteBuf.writeBytes(preLen.getBytes());
        byteBuf.writeBytes(sendData.getBytes());
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf receiveMsg = (ByteBuf) msg;
        byte[] receData = new byte[receiveMsg.readableBytes()];
        receiveMsg.readBytes(receData);
        msgResult.append(new String(receData));
        receiveMsg.release();
    }

}
