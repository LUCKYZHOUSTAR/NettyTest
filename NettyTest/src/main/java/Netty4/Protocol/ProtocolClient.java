/**     
 * @FileName: ProtocolClient.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:12:04   
 * @version V1.0     
 */
package Netty4.Protocol;

import java.nio.charset.Charset;

import NIO.NioChannelClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**  
 * @ClassName: ProtocolClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:12:04     
 */
public class ProtocolClient {

    private String           host;
    private int              port;

    private static final int MAX_FRAME_LENGTH       = 1024 * 1024;
    private static final int LENGTH_FIELD_LENGTH    = 4;
    private static final int LENGTH_FIELD_OFFSET    = 6;
    private static final int LENGTH_ADJUSTMENT      = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 0;

    public ProtocolClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {

                    ch.pipeline().addLast(
                        "decoder",
                        new ProtocolDecoder(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET,
                            LENGTH_FIELD_LENGTH, LENGTH_ADJUSTMENT, INITIAL_BYTES_TO_STRIP));
                    ch.pipeline().addLast("encoder", new ProtocolEncoder());
                    ch.pipeline().addLast(new ProtocolClientHandler());
                }

            });

            //启动客户端
            ChannelFuture f = b.connect(host, port).sync();
            while (true) {
                // 发送消息给服务器
                ProtocolMsg msg = new ProtocolMsg();
                ProtocolHeader protocolHeader = new ProtocolHeader();
                protocolHeader.setMagic((byte) 0x01);
                protocolHeader.setMsgType((byte) 0x01);
                protocolHeader.setReserve((short) 0);
                protocolHeader.setSn((short) 0);
                String body = "床前明月光疑是地上霜";
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < 2700; i++) {
                    sb.append(body);
                }

                byte[] bodyBytes = sb.toString().getBytes(Charset.forName("utf-8"));
                int bodySize = bodyBytes.length;
                protocolHeader.setLen(bodySize);

                msg.setProtocolHeader(protocolHeader);
                msg.setBody(sb.toString());

                f.channel().writeAndFlush(msg);
                Thread.sleep(2000);

            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
