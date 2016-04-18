/**     
 * @FileName: TcpClient.java   
 * @Package:Netty4.firstTest.fengzhuang   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 上午10:59:57   
 * @version V1.0     
 */
package Netty4.firstTest.fengzhuang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Netty4.example.fengzhuang.TcpClientHandler;
import Netty4.firstTest.tiaoshi.HelloClientIntHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**  
 * @ClassName: TcpClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 上午10:59:57     
 */
public class TcpClient {

    private Logger       logger    = LoggerFactory.getLogger(TcpClient.class);
    private StringBuffer msgResult = new StringBuffer();
    private String       sendData;

    public String sendData(String ip, int port, String data) {
        sendData = data;
        EventLoopGroup boss = new NioEventLoopGroup();
        Bootstrap bootstrap = createConnection(boss);

        try {
            bootstrap.connect(ip, port).channel().closeFuture().await();
            return msgResult.toString();
        } catch (InterruptedException e) {
            logger.error("TcpClient sendData InterruptedException", e);
            return null;
        } finally {
            boss.shutdownGracefully();
        }
    }

    public Bootstrap createConnection(EventLoopGroup worker) {
        Bootstrap bootstrap = new Bootstrap();
        new LengthFieldBasedFrameDecoder(65535, 0, 8, 0, 8);
        bootstrap.group(worker).channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    //                    ch.pipeline().addLast( new Decoder(),new
                    //                        clientHandler(msgResult, sendData));
                    ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(65535, 0, 8, 0, 8),
                        new clientHandler(msgResult, sendData));
                }
            });
        return bootstrap;
    }

    public static void main(String[] args) {
        String result = new TcpClient().sendData("100.66.153.98", 8888, "are you ok?");
        System.out.println(result);
    }
}
