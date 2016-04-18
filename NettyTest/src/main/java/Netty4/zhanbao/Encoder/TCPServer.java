/**     
 * @FileName: TCPServer.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午10:59:21   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**  
 * @ClassName: TCPServer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 上午10:59:21     
 */
public class TCPServer {
    private static final Logger       log = LoggerFactory.getLogger(TCPServer.class);

    private ServerBootstrap           serverBootstrap;
    private EventLoopGroup            worker;
    private EventLoopGroup            Boss;
    private config                    config;
    private DefaultEventExecutorGroup defaultEventExecutorGroup;

    public TCPServer(final config config) {
        init(config);
    }

    private void init(final config config) {
        this.serverBootstrap = new ServerBootstrap();
        this.config = config;
        //一个线程来接受，多个个线程来处理
        this.Boss = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("BossSelector_%d",
                    this.threadIndex.incrementAndGet()));
            }
        });

        //多个线程来接受，多个线程来处理
        this.worker = new NioEventLoopGroup(config.getSelectorThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);
            private int           threadTotal = config.getSelectorThreads();

            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerSelector_%d_%d", threadTotal,
                    this.threadIndex.incrementAndGet()));
            }
        });
    }

    public void start() {
        this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(config.getWorkerThreads(),
            new ThreadFactory() {
                private AtomicInteger threadIndex = new AtomicInteger(0);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "NettyServerWorkerThread_"
                                         + this.threadIndex.incrementAndGet());
                }
            });

        ServerBootstrap childHandler = this.serverBootstrap.group(this.Boss, this.worker)
            .channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
            .option(ChannelOption.SO_REUSEADDR, true)
            //
            .option(ChannelOption.SO_KEEPALIVE, false)
            //
            .childOption(ChannelOption.TCP_NODELAY, true)
            //
            .option(ChannelOption.SO_SNDBUF, config.getSocketSendBufSize())
            //
            .option(ChannelOption.SO_RCVBUF, config.getSocketRecevBufSize())
            .localAddress("100.66.162.51",this.config.getPort()).childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
//                    ch.pipeline().addLast(defaultEventExecutorGroup, new Encoder(), new Decoder(),
//                        new ServerHandler());
                     ch.pipeline().addLast(defaultEventExecutorGroup, new ServerHandler(),new Decoder());
                }

            });

        try {
            ChannelFuture sync = this.serverBootstrap.bind().sync();

        } catch (Exception e) {
            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e);
        }
    }

    public static void main(String[] args) {
        new TCPServer(new config()).start();
    }
}
