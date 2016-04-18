/**     
 * @FileName: Reactor.java   
 * @Package:Netty4.Nio   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 上午9:10:27   
 * @version V1.0     
 */
package Netty4.Nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import javassist.expr.NewArray;

/**  
 * @ClassName: Reactor   
 * @Description: 参照：http://blog.csdn.net/linxcool/article/details/7771952
 * @author: LUCKY  
 * @date:2016年4月12日 上午9:10:27     
 */
public class Reactor implements Runnable {

    public final Selector            selector;
    public final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws Exception {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();

        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),
            port);
        serverSocketChannel.socket().bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false);

        //向selector注册该channel
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //利用selectionkey的attache功能绑定acceptor，如果有事情，就会触发
        selectionKey.attach(new Acceptor(this));
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                //这个方法会一直阻塞到某个注册的通道有事件就绪
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> it = selectionKeys.iterator();
                //Selector 如果发现channel有OP_ACCEPT或者read时间发生，下列遍历就会进行
                while (it.hasNext()) {
                    //来一个事件，第一次触发一个accepter线程
                    SelectionKey selectionKey = it.next();
                    dispatch(selectionKey);
                    selectionKeys.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void dispatch(SelectionKey key) {
        Runnable r = (Runnable) key.attachment();
        if (r != null) {
            r.run();
        }
    }
}
