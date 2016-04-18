/**     
 * @FileName: client.java   
 * @Package:Netty4.firstTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月5日 下午4:59:43   
 * @version V1.0     
 */
package Netty4.firstTest;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**  
 * @ClassName: client   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月5日 下午4:59:43     
 */
public class client {

    
    public void connect(int port,String host){
        //配置客户端NIO线程组
        EventLoopGroup group=new NioEventLoopGroup();
        try {
            Bootstrap b=new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    
                    //添加处理器操作
                    ch.pipeline().addLast();
                }
                
            });
            
            
            //执行连接操作信息
            ChannelFuture f=b.connect(host, port);
            //等待客户端链路关闭
            f.channel().closeFuture().sync();
        } catch (Exception e) {
        }finally{
            
        }
    }
}
