package com.NettyTest;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
 *  每个ChannelHandler被添加到ChannelPipeline后，
 *  都会创建一个ChannelHandlerContext并与之创建的ChannelHandler关联绑定
 *  。ChannelHandlerContext允许ChannelHandler与其他的ChannelHandler实现进行交互，
 *  这是相同ChannelPipeline的一部分。
 *  ChannelHandlerContext不会改变添加到其中的ChannelHandler，因此它是安全的。
 * */
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

	// 该方法必须被重写来完成数据的读取操作
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("Server received: " + msg);
		ctx.write(msg);

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//通过此方法就可以在运行的时候动态的删除替换piple中的ChannelHandler
//		ctx.pipeline();
		ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(
				ChannelFutureListener.CLOSE);
	}

	// 用来处理异常
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();

	}

}
