package com.encoder;

import io.netty.channel.CombinedChannelDuplexHandler;

public class CharCodec extends
		CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder> {
	public CharCodec() {
		super(new ByteToCharDecoder(), new CharToByteEncoder());
	}
}
