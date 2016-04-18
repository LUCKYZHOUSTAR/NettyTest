package com.NettyTest;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;


//用来配置服务端的一些连接的相关的信息
/*•	
 * 创建ServerBootstrap实例来引导绑定和启动服务器
•	创建NioEventLoopGroup对象来处理事件，如接受新连接、接收数据、写数据等等
•	指定InetSocketAddress，服务器监听此端口
•	设置childHandler执行所有的连接请求
•	都设置完毕了，最后调用ServerBootstrap.bind() 方法来绑定服务器

 * */
public class EchoServer {

	private final int port;

	public EchoServer(int port) {
		super();
		this.port = port;
	}
	
	
	public void start() throws Exception {
		
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			
			ServerBootstrap b=new ServerBootstrap();
			//指定通道的类型，监听的本地的端口
			b.group(group).channel(NioServerSocketChannel.class).localAddress(port)
			.childHandler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					// TODO Auto-generated method stub
					//来添加需要处理消息的handler
					ch.pipeline().addLast(new EchoServerHandler());
				}

			});
			//ChannelFutrue来来判断执行的结果信息
			ChannelFuture f=b.bind().sync();
			System.out.println(EchoServer.class.getName()+"Started and linstener"+port);
		} catch (InterruptedException e) {

		}finally{
			group.shutdownGracefully().sync();
		}
	
		
	}
	
	
	public static void main(String[] args) throws Exception {
		 new EchoServer(65535).start();  
		 Thread.sleep(Integer.MAX_VALUE);
	}
}
