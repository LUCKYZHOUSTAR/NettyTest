package com.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SquareEncoder  extends MessageToByteEncoder<ByteBuf>{

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out)
			throws Exception {
		
		while(msg.readableBytes()>=2){
			int value=msg.readShort();
			out.writeChar(value*value);
		}
		
	}

}
