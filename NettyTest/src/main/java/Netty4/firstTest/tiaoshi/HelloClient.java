/**     
 * @FileName: HelloClient.java   
 * @Package:Netty4.firstTest.tongbu   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午10:46:07   
 * @version V1.0     
 */
package Netty4.firstTest.tiaoshi;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**  
 * @ClassName: HelloClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午10:46:07     
 */
public class HelloClient {
    StringBuffer   message = new StringBuffer();

    private String sendData;

    public HelloClient(String sendData) {
        this.sendData = sendData;
    }

    public String connect(String host, int port) throws Exception {

        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = createConnection(workerGroup);
//            b.group(workerGroup);
//            b.channel(NioSocketChannel.class);
//            b.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                public void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(new HelloClientIntHandler(message, sendData));
//                }
//            });

            /**
             * await()方法：Waits for this future to be completed.
             * Waits for this future until it is done, and rethrows the cause of the failure if this future
             * failed.
             */
            ChannelFuture f = b.connect(host, port).channel().closeFuture().await();
            return message.toString();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }

    public Bootstrap createConnection(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = new Bootstrap();
//        EventLoopGroup worker = new NioEventLoopGroup();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new HelloClientIntHandler(message, sendData));
                }
            });
        return bootstrap;
    }

    public static void main(String[] args) throws Exception {
        HelloClient client = new HelloClient("服务端你好吗");
        System.out.println(">>>>>>>>>>" + client.connect("100.66.153.98", 8888).toString());
    }
}
