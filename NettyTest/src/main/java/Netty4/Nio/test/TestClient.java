/**     
 * @FileName: TestClient.java   
 * @Package:Netty4.Nio.test   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 上午9:51:03   
 * @version V1.0     
 */
package Netty4.Nio.test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**  
 * @ClassName: TestClient   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 上午9:51:03     
 */
public class TestClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 8888);

        InputStream inStram = s.getInputStream();
        OutputStream outStream = s.getOutputStream();

        // 输出
        PrintWriter out = new PrintWriter(outStream, true);

        out.print("getPublicKey你好！");
        out.flush();

        s.shutdownOutput();// 输出结束

        // 输入
        Scanner in = new Scanner(inStram);
        StringBuilder sb = new StringBuilder();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            sb.append(line);
        }
        String response = sb.toString();
        System.out.println("response=" + response);
    }

}
