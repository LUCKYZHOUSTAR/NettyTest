/**     
 * @FileName: RemotingServer.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午1:42:16   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: RemotingServer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午1:42:16     
 */
public class RemotingServer {

    private static final Logger                        log  = LoggerFactory
                                                                .getLogger(RemotingServer.class);

    private ServerBootstrap                            serverBootstrap;
    private EventLoopGroup                             eventLoopGroupWorker;
    private EventLoopGroup                             eventLoopGroupBoss;
    private ConfigTest                                 configTest;

    private DefaultEventExecutorGroup                  defaultEventExecutorGroup;

    //本地server绑定的端口
    private int                                        port = 0;
    //默认请求代码处理器
    protected Pair<RequestProccessor, ExecutorService> reuestProccessor;

    public RemotingServer(final ConfigTest configTest) {
        init(configTest);
    }

    public RemotingServer(int listenPort, RequestProccessor proccessor, ExecutorService executor) {
        ConfigTest configTest = new ConfigTest();
        configTest.setListenPort(listenPort);
        init(configTest);
        this.registerProcessor(proccessor, executor);
    }

    private void init(final ConfigTest configTest) {
        this.serverBootstrap = new ServerBootstrap();
        this.configTest = configTest;
        this.eventLoopGroupBoss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                //通过ThreadFactory，可以做到自动伸缩的效果                return null;
                return new Thread(r, String.format("NettyBossSelector_%d",
                    this.threadIndex.incrementAndGet()));
            }
        });

        this.eventLoopGroupWorker = new NioEventLoopGroup(configTest.getServerSelectorThreads(),
            new ThreadFactory() {

                private AtomicInteger threadIndex = new AtomicInteger(0);
                private int           threadTotal = configTest.getServerSelectorThreads();

                public Thread newThread(Runnable r) {
                    return new Thread(r, String.format("NettyServerSelector_%d_%d", threadTotal,
                        this.threadIndex.incrementAndGet()));
                }

            });
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(
            configTest.getServerWorkerThreads(), new ThreadFactory() {

                private AtomicInteger threadIndex = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "NettyServerWorkerThread_"
                                         + this.threadIndex.incrementAndGet());
                }

            });

        ServerBootstrap childHandler = this.serverBootstrap
            .group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
            .channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
            .option(ChannelOption.SO_REUSEADDR, true).option(ChannelOption.SO_KEEPALIVE, false)
            .option(ChannelOption.TCP_NODELAY, true)
            .option(ChannelOption.SO_RCVBUF, configTest.getServerSocketRecBufSize())
            .option(ChannelOption.SO_SNDBUF, configTest.getServerSocketSndBufSize())
            .localAddress(new InetSocketAddress(configTest.getListenPort()))
            .childHandler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                        .addLast(defaultEventExecutorGroup, new Encoder(), new Decoder())
                        .addLast(
                            new IdleStateHandler(0, 0, configTest
                                .getServerChannelMaxIdelTimeSeconds()),
                            new ConnectManangerHandler(),new ServerHandler());

                }

            });
    }

    public void registerProcessor(RequestProccessor proccessor, ExecutorService executor) {
        this.reuestProccessor = new Pair<RequestProccessor, ExecutorService>(proccessor, executor);
    }

    class ConnectManangerHandler extends ChannelDuplexHandler {

        @Override
        public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = ctx.channel().remoteAddress().toString();
            log.info("NETTY SERVER PIPELINE: channelRegistered {}", remoteAddress);
            super.channelRegistered(ctx);
        }

        /* (non-Javadoc)   
         * @param ctx
         * @throws Exception   
         * @see io.netty.channel.ChannelInboundHandlerAdapter#channelUnregistered(io.netty.channel.ChannelHandlerContext)   
         */
        @Override
        public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = ctx.channel().remoteAddress().toString();
            super.channelUnregistered(ctx);
            log.info("NETTY SERVER PIPELINE: channelUnregistered, the channel[{}]", remoteAddress);
            super.channelUnregistered(ctx);
        }

        /* (non-Javadoc)   
         * @param ctx
         * @throws Exception   
         * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)   
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = ctx.channel().remoteAddress().toString();
            super.channelUnregistered(ctx);
            log.info("NETTY SERVER PIPELINE: channelActive, the channel[{}]", remoteAddress);
            super.channelActive(ctx);

        }

        /* (non-Javadoc)   
         * @param ctx
         * @throws Exception   
         * @see io.netty.channel.ChannelInboundHandlerAdapter#channelInactive(io.netty.channel.ChannelHandlerContext)   
         */
        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            final String remoteAddress = ctx.channel().remoteAddress().toString();
            super.channelUnregistered(ctx);
            log.info("NETTY SERVER PIPELINE: channelInactive, the channel[{}]", remoteAddress);
            super.channelInactive(ctx);
            super.channelInactive(ctx);

        }

        /* (non-Javadoc)   
         * @param ctx
         * @param evt
         * @throws Exception   
         * @see io.netty.channel.ChannelInboundHandlerAdapter#userEventTriggered(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
         */
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

            if (evt instanceof IdleStateEvent) {
                IdleStateEvent evnet = (IdleStateEvent) evt;
                if (evnet.state().equals(IdleState.ALL_IDLE)) {
                    final String remoteAddress = ctx.channel().remoteAddress().toString();
                    super.channelUnregistered(ctx);
                    log.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                    ctx.channel().close().addListener(ChannelFutureListener.CLOSE);
                }
            }

            ctx.fireUserEventTriggered(evt);
        }

        /* (non-Javadoc)   
         * @param ctx
         * @param cause
         * @throws Exception   
         * @see io.netty.channel.ChannelInboundHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)   
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            final String remoteAddress = ctx.channel().remoteAddress().toString();
            log.warn("NETTY SERVER PIPELINE: exceptionCaught {}", remoteAddress);
            log.warn("NETTY SERVER PIPELINE: exceptionCaught exception.", cause);
            ctx.close().addListener(ChannelFutureListener.CLOSE);
        }

    }

    class ServerHandler extends SimpleChannelInboundHandler<RemotingCommand> {

        /* (non-Javadoc)   
         * @param ctx
         * @param msg
         * @throws Exception   
         * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, RemotingCommand msg)
                                                                                   throws Exception {
            if (msg != null) {
                processRequestCommand(ctx, msg);
            }

        }

    }

    private void processRequestCommand(final ChannelHandlerContext ctx, final RemotingCommand cmd) {
        final Pair<RequestProccessor, ExecutorService> pair = this.reuestProccessor;
        if (pair != null) {
            Runnable run = new Runnable() {

                public void run() {
                    try {
                        final RemotingCommand response = pair.getObject1().processRequest(ctx, cmd);
                        if (response != null) {
                            response.setOpaque(cmd.getOpaque());
                            response.setType(RemotingCommand.RemotingCommandType.RESPONSE_COMMAND);

                            try {
                                ctx.writeAndFlush(response);
                            } catch (Exception e) {
                                log.error("process request over, but response failed", e);
                                log.error(cmd.toString());
                                log.error(response.toString());
                            }
                        } else {
                            //收到请求，但是没有返回应答，可能是processRequest中进行了应答，忽略这种情况
                        }
                    } catch (Exception e) {
                        log.error("process request exception", e);
                        log.error(cmd.toString());

                        final RemotingCommand response = RemotingCommand.createResponseCommand(
                            RemotingResponseType.SYSTEM_ERROR, //
                            "出现异常");
                        response.setOpaque(cmd.getOpaque());
                        ctx.writeAndFlush(response);
                    }
                }
            };

            try {
                //这里需要做流控，要求线程池对应的队列必须是有大小限制的
                pair.getObject2().submit(run);

            } catch (RejectedExecutionException e) {
                //每个线程10秒打印一次
                if ((System.currentTimeMillis() % 10) == 0) {
                    log.warn(ctx.channel().remoteAddress().toString()//
                             + ", too many requests and system thread pool busy, RejectedExecutionException " //
                             + pair.getObject2().toString());
                }
                final RemotingCommand response = RemotingCommand.createResponseCommand(
                    RemotingResponseType.SYSTEM_BUSY,
                    "too many requests and system thread pool busy, please try another server");
                response.setOpaque(cmd.getOpaque());
                ctx.writeAndFlush(response);
            }

        } else {
            String error = " request type not supported";
            final RemotingCommand response = RemotingCommand
                .createResponseCommand(RemotingResponseType.REQUEST_CODE_NOT_SUPPORTED, error);
            response.setOpaque(cmd.getOpaque());
            ctx.writeAndFlush(response);
            log.error(ctx.channel().remoteAddress().toString() + error);
        }
    }

    
    
    public void shutDown(){
        try {
            this.eventLoopGroupBoss.shutdownGracefully();
            this.eventLoopGroupWorker.shutdownGracefully();
            if(this.defaultEventExecutorGroup!=null){
                this.defaultEventExecutorGroup.shutdownGracefully();
            }
        } catch (Exception e) {
            log.error("NettyRemotingServer shutdown exception, ", e);
        }
    }
}
