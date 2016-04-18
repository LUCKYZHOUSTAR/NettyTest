package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;
		serverSocket = new ServerSocket(8888);

		Socket socket = null;
		//使用accept()阻塞等待客户请求，有客户

		//请求到来则产生一个Socket对象，并继续执行
		socket = serverSocket.accept();

		String line;
		// 由socket对象得到输入流，并构造相应的bufferedreader对象
		BufferedReader is = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
	
		// 由socket对象得到输入流，并构造printwriter对象
		PrintWriter os = new PrintWriter(socket.getOutputStream());

	
		BufferedReader sin = new BufferedReader(
				new InputStreamReader(System.in));
		// 由系统表述深入U设备构造对象
		System.out.println("client" + is.readLine());

		// 在标准输出上打印从客户端读入的字符串

		line = sin.readLine();

		// 从标准输入读入一字符串

		while (!line.equals("bye")) {

			// 如果该字符串为 "bye"，则停止循环

			os.println(line);

			// 向客户端输出该字符串

			os.flush();

			// 刷新输出流，使Client马上收到该字符串

			System.out.println("Server:" + line);

			// 在系统标准输出上打印读入的字符串

			System.out.println("Client:" + is.readLine());

			// 从Client读入一字符串，并打印到标准输出上

			line = sin.readLine();

			// 从系统标准输入读入一字符串

		} // 继续循环

		os.close(); // 关闭Socket输出流

		is.close(); // 关闭Socket输入流

		socket.close(); // 关闭Socket

		serverSocket.close(); // 关闭ServerSocket

	}
}
