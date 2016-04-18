package com.guide;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class BootstrapClient {

	public void bootStrap(){
		Bootstrap bootstrap=new Bootstrap();
		bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
		.handler(new SimpleChannelInboundHandler<ByteBuf>() {

			@Override
			protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
					throws Exception {
					System.out.println("receive data");
					msg.clear();
				
			}
			
		});
		
		
		ChannelFuture future=bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
		future.addListener(new ChannelFutureListener() {
			
			public void operationComplete(ChannelFuture future) throws Exception {
				// TODO Auto-generated method stub
				if(future.isSuccess()){
				    
					System.out.println("Connection established");
				}else {
					System.err.println("Connection attempt failed");
					future.cause().printStackTrace();
				}
			}
		});
	}
}
