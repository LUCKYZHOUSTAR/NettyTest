package com.guide;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BootstrapSharingEventLoopGroup {

	public void bootStrap() {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
				.channel(NioServerSocketChannel.class)
				.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

					@Override
					protected void channelRead0(ChannelHandlerContext ctx,
							ByteBuf msg) throws Exception {
						// TODO Auto-generated method stub

					}

					@Override
					public void channelActive(ChannelHandlerContext ctx)
							throws Exception {
						// TODO Auto-generated method stub
						super.channelActive(ctx);

						Bootstrap bootstrap = new Bootstrap();
						bootstrap.channel(NioSocketChannel.class).handler(
								new SimpleChannelInboundHandler<ByteBuf>() {

									@Override
									protected void channelRead0(
											ChannelHandlerContext ctx,
											ByteBuf msg) throws Exception {
										// TODO Auto-generated method stub

										System.out.println("receive data");
										msg.clear();
									}

								});
					}

				});

		ChannelFuture future = bootstrap.bind(new InetSocketAddress(8080));
		future.addListener(new ChannelFutureListener() {
			public void operationComplete(ChannelFuture channelFuture)
					throws Exception {
				if (channelFuture.isSuccess()) {
					System.out.println("Server bound");
				} else {
					System.err.println("Bound attempt failed");
					channelFuture.cause().printStackTrace();
				}
			}
		});
	}
}
