package com.ssl;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpCompressionInitializer extends ChannelInitializer<Channel> {

	
	private final boolean client;
	
	
	
	public HttpCompressionInitializer(boolean client) {
		super();
		this.client = client;
	}



	@Override
	protected void initChannel(Channel ch) throws Exception {
		// TODO Auto-generated method stub
		 ChannelPipeline pipeline = ch.pipeline();
	        if (client) {
	            pipeline.addLast("codec", new HttpClientCodec());
	            pipeline.addLast("decompressor", new HttpContentDecompressor());
	        } else {
	            pipeline.addLast("codec", new HttpServerCodec());
	            pipeline.addLast("compressor", new HttpContentCompressor());
	        }
	}

}
