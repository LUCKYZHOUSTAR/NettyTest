/**     
 * @FileName: BaseMsg.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:56:46   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

import java.io.Serializable;

/**  
 * @ClassName: BaseMsg   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:56:46     
 */
public class BaseMsg implements Serializable {

    /**   
     * @Fields: serialVersionUID  
     * @Desc: 
     */
    private static final long serialVersionUID = -3407435242760760914L;

    private MsgType           type;

    //必须唯一，否则会出现channel调用混乱
    private String            clientId;

    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    /**   
     * @return type   
     */
    public MsgType getType() {
        return type;
    }

    /**     
     * @param type the type to set     
     */
    public void setType(MsgType type) {
        this.type = type;
    }

    /**   
     * @return clientId   
     */
    public String getClientId() {
        return clientId;
    }

    /**     
     * @param clientId the clientId to set     
     */
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    
    

}
