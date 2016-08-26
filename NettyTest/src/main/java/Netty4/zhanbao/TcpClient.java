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
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @ClassName: TcpClient
 * @Description:
 * @author: LUCKY
 * @date:2016年4月12日 下午3:13:15
 */
public class TcpClient {
	public static String HOST = "localhost";
	public static int PORT = 9999;

	public static void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();

		try {
			b.group(group).channel(NioSocketChannel.class);
			b.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ChannelPipeline pipeline = ch.pipeline();
					pipeline.addLast("handler", new TcpClientHandler());
				}
			});

			// 设置tcp的参数信息
			b.option(ChannelOption.SO_KEEPALIVE, true);
			b.option(ChannelOption.TCP_NODELAY, true);
			// 建立连接操作,同步建立连接请求
			ChannelFuture channelFuture = b.connect(host, port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		connect(HOST, PORT);
	}
}
