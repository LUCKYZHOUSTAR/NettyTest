/**     
 * @FileName: EventTest.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:35:30   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import io.netty.channel.Channel;

/**  
 * @ClassName: EventTest   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:35:30     
 */
public class EventTest {

    private final EventTypeTest type;
    private final String remoteAddr;
    private final Channel channel;
    public EventTest(EventTypeTest type, String remoteAddr, Channel channel) {
        this.type = type;
        this.remoteAddr = remoteAddr;
        this.channel = channel;
    }
    /**   
     * @return type   
     */
    public EventTypeTest getType() {
        return type;
    }
    /**   
     * @return remoteAddr   
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }
    /* (non-Javadoc)   
     * @return   
     * @see java.lang.Object#toString()   
     */  
    @Override
    public String toString() {
        return "EventTest [type=" + type + ", remoteAddr=" + remoteAddr + ", channel=" + channel
               + "]";
    }
    
    
    
    
}
