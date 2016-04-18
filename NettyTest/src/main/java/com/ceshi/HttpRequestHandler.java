package com.ceshi;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

import java.io.File;
import java.net.URL;

public class HttpRequestHandler extends
		SimpleChannelInboundHandler<FullHttpRequest> {

	private final String wsUri;
	private static final File INDEX;
	static {
		URL location = HttpRequestHandler.class.getProtectionDomain()
				.getCodeSource().getLocation();
		try {
			String path = location.toURI() + "index.html";
			path = path.contains("file:") ? path : path.substring(5);
			INDEX = new File(path);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	
	
	
	
	
	
	public HttpRequestHandler(String wsUri) {
		super();
		this.wsUri = wsUri;
	}







	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
