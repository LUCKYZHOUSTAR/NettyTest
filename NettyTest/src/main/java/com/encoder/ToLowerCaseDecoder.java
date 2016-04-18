package com.encoder;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class ToLowerCaseDecoder  extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub
		
		if(in.readableBytes()<2){
			return;
		}
		
		ByteBuf buf=ctx.alloc().buffer();
		while(in.readableBytes()>=2){
			char c=in.readChar();
			buf.writeChar(Character.toLowerCase(c));
			
		}
		out.add(buf);
	}

}
