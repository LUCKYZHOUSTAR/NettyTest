/**     
 * @FileName: AskMsg.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:56:20   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: AskMsg   
 * @Description:请求类型的消息 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:56:20     
 */
public class AskMsg extends BaseMsg {

    public AskMsg() {
        super();
        setType(MsgType.ASK);
    }
    
    private AskParams params;

    /**   
     * @return params   
     */
    public AskParams getParams() {
        return params;
    }

    /**     
     * @param params the params to set     
     */
    public void setParams(AskParams params) {
        this.params = params;
    }
    
    
    
}
