package NIO.Selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorClientTest {

	private Selector selector;
	private ByteBuffer outBuff=ByteBuffer.allocate(1024);
	private ByteBuffer intBuffer=ByteBuffer.allocate(1024);
	private int keys=0;
	private SocketChannel channel=null;
	
	public void initClient() throws Exception
	{
		//获得一个socketsocket通道，并没有进行连接
		channel=SocketChannel.open();
		//获得一个通道管理器
		selector=Selector.open();
		
		//设置为非阻塞
		channel.configureBlocking(false);
		//连接服务器
		channel.connect(new InetSocketAddress("127.0.0.1", 8888));
		//注册客户端连接服务器的事件
		channel.register(this.selector, SelectionKey.OP_CONNECT);
		
		
	}
	
	
	public void listen() throws IOException{
		while(true){
			keys=this.selector.select();
			if(keys>0){
				//获得渠道管理器，事件注册的集合
			}
		}
	}
}
