package NIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioChannelServer {

	private ByteBuffer buff = ByteBuffer.allocate(1024);
	// 创建一个int缓冲区的视图，此缓冲区内容的更改在新缓冲区是可见的。反之亦然
	private IntBuffer intBuffer = buff.asIntBuffer();
	private SocketChannel clientChannel = null;
	private ServerSocketChannel serverSocketChannel = null;

	public void openChannel() throws Exception {
		
		// 建立一个新的连接到的通道
		serverSocketChannel = ServerSocketChannel.open();
		// 为新的通道设置访问的端口
		serverSocketChannel.socket().bind(new InetSocketAddress(8888));
		System.out.println("服务器通道已经打开");
	}
	
	
	public void waitReqConn() throws IOException{
		while(true){
			clientChannel=serverSocketChannel.accept();
			if(null!=clientChannel){
				System.out.println("新的连接加入");
			}
			
			processReq();
			clientChannel.close();
			
			
		}
	}
	
	
	public void processReq() throws IOException{
		System.out.println("开始读取和处理客户端请求");
		buff.clear();
		clientChannel.read(buff);
		int result=intBuffer.get(0)+intBuffer.get(1);
		buff.flip();
		buff.clear();
		//修改视图，原来的缓冲区也会变化
		intBuffer.put(0, result);
		clientChannel.write(buff);
		System.out.println("读取和处理客户单数据完成");
	}
	
	
	public void start(){
		try {
			//打开通道
			openChannel();
			//监听等待客户端请求
			waitReqConn();
			clientChannel.close();
			System.out.println("服务端处理完毕");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	
	public static void main(String[] args) {
		new NioChannelServer().start();
	}
}
