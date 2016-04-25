/**     
 * @FileName: TcpServer.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:37:26   
 * @version V1.0     
 */
package Netty4.zhanbao;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**  
 * @ClassName: TcpServer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:37:26     
 */
public class TcpServer {

    private static final String IP            = "localhost";
    private static final int    PORT          = 9999;

    /*用于分配处理业务线程的线程组个数*/

    protected static final int  BIZGROUPSIZE  = Runtime.getRuntime().availableProcessors() * 2;

    /*业务出现线程大小*/

    protected static final int  BIZTHREADSIZE = 4;

    /* 
     * NioEventLoopGroup实际上就是个线程池, 
     * NioEventLoopGroup在后台启动了n个NioEventLoop来处理Channel事件, 
     * 每一个NioEventLoop负责处理m个Channel, 
     * NioEventLoopGroup从NioEventLoop数组里挨个取出NioEventLoop来处理Channel 
     */

    protected static void connect() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);
        EventLoopGroup workerGroup = new NioEventLoopGroup(BIZTHREADSIZE);
        try {
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true);
            b.childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast(new TcpServerHandler());
                }

            });

            ChannelFuture channelFuture = b.bind(IP, PORT).sync();
            Thread.sleep(Integer.MAX_VALUE);

            //等待服务端端口关闭
//            channelFuture.channel().closeFuture().sync();
            System.out.println("TCP服务器已经启动");
        } catch (Exception e) {
        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        connect();
        System.out.println("服务端已经启动");
    }
}
