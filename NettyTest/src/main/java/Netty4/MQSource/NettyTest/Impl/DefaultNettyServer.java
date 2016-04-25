package Netty4.MQSource.NettyTest.Impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Netty4.MQSource.NettyTest.NettyTest.NettyRemotingServer;
import Netty4.MQSource.NettyTest.NettyTest.NettyRequestProcessor;

public class DefaultNettyServer {
    private Logger                logger          = LoggerFactory
                                                      .getLogger(DefaultNettyServer.class);

    private int                   accepts         = 3000;
    private int                   corePoolSize    = 1;
    private int                   maximumPoolSize = 5;
    private long                  keepAliveTime   = 1000L;
    private String                port;
    private NettyRequestProcessor processor;

    public void init() {
        BlockingQueue workQueue = new LinkedBlockingQueue(this.accepts);
        ExecutorService executor = new ThreadPoolExecutor(this.corePoolSize, this.maximumPoolSize,
            this.keepAliveTime, TimeUnit.MILLISECONDS, workQueue);
        NettyRemotingServer nserver = new NettyRemotingServer(this.port, this.processor, executor);
        nserver.start();
    }

    public void setAccepts(int accepts) {
        this.accepts = accepts;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setProcessor(NettyRequestProcessor processor) {
        this.processor = processor;
    }
}
