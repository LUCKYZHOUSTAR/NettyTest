/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Netty4.MQSource.NettyTest.NettyTest;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Netty4.MQSource.NettyTest.common.RemotingUtil;
import Netty4.MQSource.NettyTest.protocol.RemotingCommand;


/**
 * @author LUCKY
 *
 */
public class NettyDecoder extends LengthFieldBasedFrameDecoder {
    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);
    private static final int FRAME_MAX_LENGTH = //
            Integer.parseInt(System.getProperty("com.remoting.frameMaxLength", "8388608"));


    public NettyDecoder() {
        super(FRAME_MAX_LENGTH, 0, 8, 0, 8);
    }


    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();
            int len = byteBuffer.capacity();
            byte[] bodyData = new byte[len];
            byteBuffer.get(bodyData);
            RemotingCommand cmd = RemotingCommand.createRequestCommand();
            cmd.setBody(bodyData);
            return cmd;
        } catch (Exception e) {
            log.error("decode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            RemotingUtil.closeChannel(ctx.channel());
        } finally {
            if (null != frame) {
                frame.release();
            }
        }

        return null;
    }
    @Override
    public long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        byte[] lendata = new byte[8];
        buf.getBytes(0, lendata);
        int frameLength = Integer.parseInt(new String(lendata));
        return frameLength;
    }
}
