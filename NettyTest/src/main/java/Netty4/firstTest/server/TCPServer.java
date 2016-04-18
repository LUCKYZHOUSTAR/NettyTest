/**     
 * @FileName: TCPServer.java   
 * @Package:Netty4.firstTest.server   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 下午4:15:16   
 * @version V1.0     
 */
package Netty4.firstTest.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**  
 * @ClassName: TCPServer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 下午4:15:16     
 */
public class TCPServer {

    private EventLoopGroup  boss;
    private EventLoopGroup  worker;
    private ServerBootstrap serverBootstrap;
    private int             port = 8888;

    public TCPServer() {
        init(new ServerBootstrap());
    }

    private void init(ServerBootstrap serverBootstrap) {
        this.serverBootstrap = serverBootstrap;
        this.boss = new NioEventLoopGroup(1);
        this.worker = new NioEventLoopGroup();
        serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<Channel>() {
                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new Encoder(),new Decoder(),new TCPHandler());
//                    ch.pipeline().addLast(new Decoder());
//                    ch.pipeline().addLast(new Encoder());
//                    ch.pipeline().addLast(new TCPHandler());
                }

            });
    }

    public void connect() throws Exception {
        this.serverBootstrap.localAddress("100.66.162.51",port).bind().sync();
    }

    
    public static void main(String[] args) throws Exception {
        new TCPServer().connect();
    }
}
