package com.ceshi;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

public class AbsIntegerEncoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg,
			List<Object> out) throws Exception {
		// TODO Auto-generated method stub

		while (msg.readableBytes() >= 4) {
			int value = Math.abs(msg.readInt());
			out.add(value);
		}
	}

	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

}
