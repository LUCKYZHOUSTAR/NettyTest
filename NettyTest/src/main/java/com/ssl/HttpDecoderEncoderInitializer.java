package com.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpDecoderEncoderInitializer extends ChannelInitializer<Channel>{

	
	private final boolean client;
	
	public HttpDecoderEncoderInitializer(boolean client) {
		super();
		this.client = client;
	}

	
	
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline=ch.pipeline();
		 if (client) {
	            pipeline.addLast("decoder", new HttpResponseDecoder());
	            pipeline.addLast("encoder", new HttpRequestEncoder());
	        } else {
	            pipeline.addLast("decoder", new HttpRequestDecoder());
	            pipeline.addLast("encoder", new HttpResponseEncoder());
	        }
		
	}

}
