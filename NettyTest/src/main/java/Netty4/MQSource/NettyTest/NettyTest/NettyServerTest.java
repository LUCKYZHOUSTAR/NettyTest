package Netty4.MQSource.NettyTest.NettyTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.BasicConfigurator;

import Netty4.MQSource.NettyTest.protocol.RemotingCommand;

public class NettyServerTest {

    public static void main(String[] args) {
        BasicConfigurator.configure();
        //服务端具体的业务逻辑处理
        NettyRequestProcessor processor = new NettyRequestProcessor() {

            public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
                                                                                                     throws Exception {
                // TODO Auto-generated method stub
                System.out.println("receive netty RemotingCommand " + request.toString()
                                   + request.getBody().length);
                RemotingCommand cmd = RemotingCommand
                    .createResponseCommand("process netty RemotingCommand ok".getBytes());
                return cmd;
            }
        };

        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>(1000);
        ExecutorService executor = new ThreadPoolExecutor(1, 5, 1000, TimeUnit.SECONDS, workQueue);
        NettyRemotingServer nServer = new NettyRemotingServer("8888", processor, executor);
        nServer.start();
    }
}
