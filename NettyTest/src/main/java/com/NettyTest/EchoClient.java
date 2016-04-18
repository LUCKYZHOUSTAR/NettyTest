package com.NettyTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/*
 * •创建Bootstrap对象用来引导启动客户端
 •	创建EventLoopGroup对象并设置到Bootstrap中，EventLoopGroup可以理解为是一个线程池，这个线程池用来处理连接、接受数据、发送数据
 •	创建InetSocketAddress并设置到Bootstrap中，InetSocketAddress是指定连接的服务器地址
 •	添加一个ChannelHandler，客户端成功连接服务器后就会被执行
 •	调用Bootstrap.connect()来连接服务器
 •	最后关闭EventLoopGroup来释放资源

 * */
public class EchoClient {

	private final String host;
	private final int port;

	public EchoClient(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public void start() throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			// 每个Channel都会分配一个ChannelPipeline和ChannelConfig。ChannelConfig负责设置并存储配置
			b.group(group).channel(NioSocketChannel.class).localAddress(port)
					.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) throws Exception {
							// ChannelPipeline容纳了使用的ChannelHandler实例，这些ChannelHandler将处理通道传递的“入站”和“出站”数据。ChannelHandler的实现允许你改变数据状态和传输数据
							// TODO Auto-generated method stub
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});

			ChannelFuture f = b.connect().sync();
			f.addListener(new ChannelFutureListener() {

				public void operationComplete(ChannelFuture future)
						throws Exception {
					if (future.isSuccess()) {
						System.out.println("write success");
					} else {
						System.err.println("write error");
						future.cause().printStackTrace();
					}
				}
			});
			// f.channel().closeFuture().sync();
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			group.shutdownGracefully().sync();
		}
	}

	public static void main(String[] args) throws Exception {
		new EchoClient("100.66.162.68", 65535).start();
		Thread.sleep(Integer.MAX_VALUE);
	}

}
