/**     
 * @FileName: TcpClient.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:13:15   
 * @version V1.0     
 */
package Netty4.zhanbao.LineBasedFrameDecoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

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

        try {
            b.group(group).channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
//                    ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                    ch.pipeline().addLast(new StringDecoder());
                    pipeline.addLast("handler", new TcpClientHandler());
                }
            });

            //设置tcp的参数信息
            b.option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = b.connect(host, port).sync();
            //等待服务端监听端口关闭,会一直阻塞，等待服务端关闭后，才关闭,异步阻塞，等待服务端链路关闭后，才会退出
            channelFuture.channel().closeFuture().sync();
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
