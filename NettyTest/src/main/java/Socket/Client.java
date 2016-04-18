package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) throws Exception, IOException {

		
		
		Socket socket = new Socket("localhost", 8888);
		// 向本机的8888端口发出客户请求
		BufferedReader sin = new BufferedReader(
				new InputStreamReader(System.in));

		// 由系统标准输入设备构造BufferedReader对象
		PrintWriter os = new PrintWriter(socket.getOutputStream());
		// 由socket对象得到输入流，病构造printWriter对象
		BufferedReader is = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		// 由Socket对象得到输入流，并构造相应的BufferedReader对象
		String readline;
		readline = sin.readLine();// 从系统标准输入读入一字符串
		while (!readline.equals("bye")) {
			// 若标准输入的读入的字符串为bye则停止循环
			os.print(readline);
			// 从系统标准输入读入的字符串到server
			os.flush();
			// 刷新输出流，使server马上收到该字符串
			System.out.println("Client:" + readline);

			// 在系统标准输出上打印读入的字符串

			System.out.println("Server:" + is.readLine());

			// 从Server读入一字符串，并打印到标准输出上

			readline = sin.readLine(); // 从系统标准输入读入一字符串

		}

		os.close(); // 关闭Socket输出流
		is.close(); // 关闭Socket输入流

		socket.close(); // 关闭Socket

	}
}
