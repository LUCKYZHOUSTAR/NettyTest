/**     
 * @FileName: TcpClientHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:35:32   
 * @version V1.0     
 */
package Netty4.zhanbao;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @ClassName: TcpClientHandler
 * @Description:
 * @author: LUCKY
 * @date:2016年4月12日 下午3:35:32
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

	private int counter;
	private byte[] req;

	public TcpClientHandler() {
		req = ("QUERY TIME ORDER" + System.getProperty("line.separator"))
				.getBytes();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 循环发送100条消息
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
			message = Unpooled.buffer(req.length);
			message.writeBytes(req);
			ctx.writeAndFlush(message);
		}

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req, "UTF-8");
		System.out.println("Now is:" + body + "; the counter is:" + ++counter);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		System.out.println("我已经读取完毕了");
		ctx.channel().close();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}

}
