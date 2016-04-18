package com.encoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

public class IntegerToStringDecoder  extends MessageToMessageDecoder<Integer>{

	@Override
	protected void decode(ChannelHandlerContext ctx, Integer msg,
			List<Object> out) throws Exception {
		
		out.add(String.valueOf(msg));
		
	}

}
