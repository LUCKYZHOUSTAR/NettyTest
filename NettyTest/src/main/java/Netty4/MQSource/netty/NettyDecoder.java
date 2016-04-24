package Netty4.MQSource.netty;

import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyDecoder extends LengthFieldBasedFrameDecoder {
    private static final Logger log              = LoggerFactory.getLogger(NettyDecoder.class);
    private static final int    FRAME_MAX_LENGTH = //
                                                 Integer.parseInt(System.getProperty(
                                                     "com.rocketmq.remoting.frameMaxLength",
                                                     "8388608"));

    public NettyDecoder() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;
            }

            ByteBuffer byteBuf = frame.nioBuffer();
            return byteBuf;
        } catch (Exception e) {
        } finally {
            if (null != frame) {
                frame.release();
            }
        }

        return null;
    }

}
