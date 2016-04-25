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
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Netty4.MQSource.NettyTest.common.RemotingUtil;
import Netty4.MQSource.NettyTest.protocol.RemotingCommand;


/**
 * @author LUCKY
 *
 */
public class NettyEncoder extends MessageToByteEncoder<RemotingCommand> {
    private static final Logger log = LoggerFactory.getLogger(RemotingUtil.RemotingLogName);

    @Override
    public void encode(ChannelHandlerContext ctx, RemotingCommand cmd, ByteBuf out)
            throws Exception {
        try {
            int length = 0;
            if (cmd.getBody() != null) {
                length += cmd.getBody().length;
            }

            ByteBuffer result = ByteBuffer.allocate(8 + length);
            String preLen = String.format("%08d", length);
            // length
            result.put(preLen.getBytes());
            // body data;
            if (cmd.getBody() != null) {
                result.put(cmd.getBody());
            }

            result.flip();

            out.writeBytes(result);
        } catch (Exception e) {
            log.error("encode exception, " + RemotingUtil.parseChannelRemoteAddr(ctx.channel()), e);
            if (cmd != null) {
                log.error(cmd.toString());
            }
            RemotingUtil.closeChannel(ctx.channel());
        }
    }
}
