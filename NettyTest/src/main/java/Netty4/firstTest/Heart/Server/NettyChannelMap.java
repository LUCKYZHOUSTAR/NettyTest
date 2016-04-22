/**     
 * @FileName: NettyChannelMap.java   
 * @Package:Netty4.firstTest.Heart.Server   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:51:28   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Server;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**  
 * @ClassName: NettyChannelMap   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:51:28     
 */
public class NettyChannelMap {

    private static Map<String, SocketChannel> map = new ConcurrentHashMap<String, SocketChannel>();

    public static void add(String clientId, SocketChannel socketChannel) {
        map.put(clientId, socketChannel);
    }

    public static Channel get(String clientId) {
        return map.get(clientId);
    }

    public static void remove(SocketChannel socketChannel) {
        for (String key : map.keySet()) {
            if (map.get(key) == socketChannel) {
                map.remove(key);
            }
        }
    }
}
