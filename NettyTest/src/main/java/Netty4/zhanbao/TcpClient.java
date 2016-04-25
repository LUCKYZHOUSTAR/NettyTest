/**     
 * @FileName: TcpClient.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:13:15   
 * @version V1.0     
 */
package Netty4.zhanbao;

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
                    //使用LengthFieldBasedFrameDecoder作为decoder实现，LengthFieldBasedFrameDecoder构造函数，
                    //第一个参数为信息最大长度，超过这个长度回报异常，
                    // 第二参数为长度属性的起始（偏移）位，我们的协议中长度是0到第3个字节，所以这里写0，
                    //第三个参数为“长度属性”的长度，我们是4个字节，所以写4，
                    //第四个参数为长度调节值，
                    //在总长被定义为包含包头长度时，修正信息长度，第五个参数为跳过的字节数，
                    //根据需要我们跳过前4个字节，以便接收端直接接受到不含“长度属性”的内容。
                    pipeline.addFirst("decoder", new LengthFieldBasedFrameDecoder(100000000, 0, 4,
                        0, 4));
                    pipeline.addLast("handler", new TcpClientHandler());
                }
            });

            //设置tcp的参数信息
            b.option(ChannelOption.SO_KEEPALIVE, true)
            .option(ChannelOption.TCP_NODELAY, true);
            ChannelFuture channelFuture = b.connect(host, port).sync();
            //等待服务端监听端口关闭,会一直阻塞，等待服务端关闭后，才关闭,异步阻塞，等待服务端链路关闭后，才会退出
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程池资源
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws Exception {
        connect(HOST, PORT);
    }
}
