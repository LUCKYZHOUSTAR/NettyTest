package com.guide;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class BootstrapClientWithOptionsAndAttrs {

	public void bootstrap() {
		final AttributeKey<Integer> id = null;

		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(new NioEventLoopGroup())
				.channel(NioSocketChannel.class)
				.handler(new SimpleChannelInboundHandler<ByteBuf>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						// TODO Auto-generated method stub

						System.out.println("Receive data");
						msg.clear();
					}

					@Override
					public void channelRegistered(ChannelHandlerContext ctx)
							throws Exception {
						// TODO Auto-generated method stub
						super.channelRegistered(ctx);

						Integer idValueInteger = ctx.channel().attr(id).get();
					}

				});
		
		
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
		ChannelFuture future=bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
		future.syncUninterruptibly();
	}
}
