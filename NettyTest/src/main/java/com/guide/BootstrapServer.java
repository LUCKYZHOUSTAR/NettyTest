package com.guide;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class BootstrapServer {

	public void bootstrap(){
		
		ServerBootstrap bootstrap=new ServerBootstrap();
		bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup()).channel(NioServerSocketChannel.class)
		.childHandler(new SimpleChannelInboundHandler<ByteBuf>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
					throws Exception {
				// TODO Auto-generated method stub
				System.out.println("Receive data");
				msg.clear();
			}
			
		});
		
		ChannelFuture future=bootstrap.bind(new InetSocketAddress(8080));
		future.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(future.isSuccess()){
					System.out.println("Server bound");
				}else {
					System.out.println("Sound attempt failed");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
