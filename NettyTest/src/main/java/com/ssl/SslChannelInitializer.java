package com.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslHandler;

public class SslChannelInitializer  extends ChannelInitializer<Channel>{

	private final SSLContext context;
	private final boolean client;
	private final boolean startTls;
	
	
	
	public SslChannelInitializer(SSLContext context, boolean client,
			boolean startTls) {
		super();
		this.context = context;
		this.client = client;
		this.startTls = startTls;
	}



	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		SSLEngine engine=context.createSSLEngine();
		engine.setUseClientMode(client);
		//SslHandler必须要添加到ChannelPipeline的第一个位置
		//最先添加的SslHandler会啊在其他Handler处理逻辑数据之前对数据进行加密，
		//		从而确保Netty服务端的所有的Handler的变化都是安全的。
		ch.pipeline().addFirst("ssl",new SslHandler(engine, startTls));
		
	}
	
}
