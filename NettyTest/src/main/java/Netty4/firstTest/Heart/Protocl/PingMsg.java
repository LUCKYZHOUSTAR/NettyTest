/**     
 * @FileName: PingMsg.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午11:04:22   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: PingMsg   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午11:04:22     
 */
public class PingMsg extends BaseMsg{
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }
}
