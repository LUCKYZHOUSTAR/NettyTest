/**     
 * @FileName: Acceptor.java   
 * @Package:Netty4.Nio   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 上午9:27:05   
 * @version V1.0     
 */
package Netty4.Nio;

import java.nio.channels.SocketChannel;

/**  
 * @ClassName: Acceptor   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 上午9:27:05     
 */
public class Acceptor implements Runnable{

    private Reactor reactor;
    public Acceptor(Reactor reactor){
        this.reactor=reactor;
    }
    
    
    
    
    public void run() {
        try {
            SocketChannel socketChannel=reactor.serverSocketChannel.accept();
            if(socketChannel!=null){
                new SocketReadHandler(reactor.selector, socketChannel);
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }

}
