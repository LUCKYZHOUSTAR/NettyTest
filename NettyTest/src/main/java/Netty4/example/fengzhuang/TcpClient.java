/**     
 * @FileName: TcpClient.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:13:15   
 * @version V1.0     
 */
package Netty4.example.fengzhuang;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**  
 * @ClassName: TcpClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:13:15     
 */
public class TcpClient {
    public static String HOST = "localhost";
    public static int    PORT = 9999;

    public static void connect(String host, int port) {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
      final TcpClientHandler tcpClientHandler= new TcpClientHandler();

        try {
            b.group(group).channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("handler", tcpClientHandler);
                }
            });

            //设置tcp的参数信息
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.connect(host, port).sync().channel().writeAndFlush(Unpooled.copiedBuffer("你红啊吗".getBytes()));
//            ChannelFuture channelFuture = b.connect(host, port).sync();
            //等待服务端监听端口关闭,会一直阻塞，等待服务端关闭后，才关闭,异步阻塞，等待服务端链路关闭后，才会退出
//            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            //优雅退出，释放线程池资源
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        connect(HOST, PORT);
    }
}
