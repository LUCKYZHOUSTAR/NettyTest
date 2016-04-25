package Netty4.MQSource.NettyTest.NettyTest;

import io.netty.channel.ChannelHandlerContext;
import Netty4.MQSource.NettyTest.protocol.RemotingCommand;

public interface NettyRequestProcessor {

    
   
    RemotingCommand processRequest(ChannelHandlerContext ctx,RemotingCommand request)throws Exception;
}
