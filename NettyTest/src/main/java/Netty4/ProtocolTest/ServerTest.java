/**     
 * @FileName: ServerTest.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午3:24:53   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.BasicConfigurator;

/**  
 * @ClassName: ServerTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午3:24:53     
 */
public class ServerTest {

    public static void main(String[] args) {

        BasicConfigurator.configure();
        RequestProccessor proccessor = new RequestProccessor() {

            public RemotingCommand processRequest(ChannelHandlerContext ctx, RemotingCommand request)
                                                                                                     throws Exception {
                System.out.println("receive netty RemotingCommand " + request.toString()
                                   + request.getBody().length);
                RemotingCommand cmd = RemotingCommand
                    .createResponseCommand("process netty RemotingCommand ok".getBytes());
                return cmd;
            }
        };

        BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>(1000);
        ExecutorService executor = new ThreadPoolExecutor(1, 5, 1000, TimeUnit.MICROSECONDS,
            workQueue);
        RemotingServer nServer = new RemotingServer(8888, proccessor, executor);
        nServer.start();
        DefaultSocketClient.sendReceive("", 8888, "");
        System.out.println("end netty server ok");

    }
}
