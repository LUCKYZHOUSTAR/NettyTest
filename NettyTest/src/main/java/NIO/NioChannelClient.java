package NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

public class NioChannelClient {

	private SocketChannel channel=null;
	private ByteBuffer buff=ByteBuffer.allocate(1024);
	private IntBuffer intBuffer=buff.asIntBuffer();
	
	
	//与服务器指定的地址和端口建立连接通道
	public SocketChannel connect() throws Exception{
		return SocketChannel.open(new InetSocketAddress("127.0.0.1",8888));
		
	}
	
	//发送加发请求到服务器
	public void sendRequest(int a,int b) throws Exception{
		buff.clear();
		intBuffer.put(0,a);
		intBuffer.put(1, b);
		channel.write(buff);
		System.out.println("发送加发请求");
	}
	
	public int receiveResutl() throws Exception{
		buff.clear();
		channel.read(buff);
		return intBuffer.get(0);
	}
	
	
	
	public int getSum(int a,int b){
		int result=0;
		try {
			channel=connect();
			sendRequest(a, b);
			result=receiveResutl();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	public static void main(String[] args) {
		int result=new NioChannelClient().getSum(56, 34);
		System.out.println("最后的结果为"+result);
	}
}
