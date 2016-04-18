/**     
 * @FileName: SocketReadHandler.java   
 * @Package:Netty4.Nio   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 上午9:22:05   
 * @version V1.0     
 */
package Netty4.Nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;


/**  
 * @ClassName: SocketReadHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 上午9:22:05     
 */
public class SocketReadHandler implements Runnable{

    private SocketChannel socketChannel;
    public SocketReadHandler(Selector selector,SocketChannel socketChannel) throws Exception{
        this.socketChannel=socketChannel;
        socketChannel.configureBlocking(false);
        SelectionKey selectionKey=socketChannel.register(selector, 0);
        //将selectionkey绑定为本handler，下一步事件触发，将调用本类的run方法
        selectionKey.attach(this);
        //同时将selectionkey标记为可读，以便读取
        selectionKey.interestOps(selectionKey.OP_READ);
        selector.wakeup();
    }
    
    
    public void run() {
        ByteBuffer inputBuffer=ByteBuffer.allocate(1024);
        inputBuffer.clear();
        try {
            socketChannel.read(inputBuffer);
            //激活线程池，处理这些request
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
