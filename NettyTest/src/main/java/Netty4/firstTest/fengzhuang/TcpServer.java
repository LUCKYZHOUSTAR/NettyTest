/**     
 * @FileName: TcpServer.java   
 * @Package:Netty4.firstTest.fengzhuang   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午11:32:01   
 * @version V1.0     
 */
package Netty4.firstTest.fengzhuang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**  
 * @ClassName: TcpServer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午11:32:01     
 */
public class TcpServer {
    private Logger       logger = LoggerFactory.getLogger(TcpServer.class);
    private final String ip     = "100.66.153.98";
    private final int    port   = 8888;

    private void connect() {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(8);

        try {
            //            new LengthFieldBasedFrameDecoder(65535, 0, 8, 0, 8),
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                .localAddress(ip, port).childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 8, 0, 8), new TcpServerHandler());
                    }
                });
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("服务端出现异常", e);

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new TcpServer().connect();
    }
}
