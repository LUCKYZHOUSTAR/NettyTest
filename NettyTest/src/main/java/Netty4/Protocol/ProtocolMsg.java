/**     
 * @FileName: ProtocolMsg.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 上午9:58:37   
 * @version V1.0     
 */
package Netty4.Protocol;

/**  
 * @ClassName: ProtocolMsg   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 上午9:58:37     
 */
public class ProtocolMsg {

    
    private ProtocolHeader protocolHeader=new ProtocolHeader();
    
    private String body;

    /**   
     * @return protocolHeader   
     */
    public ProtocolHeader getProtocolHeader() {
        return protocolHeader;
    }

    /**     
     * @param protocolHeader the protocolHeader to set     
     */
    public void setProtocolHeader(ProtocolHeader protocolHeader) {
        this.protocolHeader = protocolHeader;
    }

    /**   
     * @return body   
     */
    public String getBody() {
        return body;
    }

    /**     
     * @param body the body to set     
     */
    public void setBody(String body) {
        this.body = body;
    }
    
    
}
