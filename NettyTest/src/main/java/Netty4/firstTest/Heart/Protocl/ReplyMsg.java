/**     
 * @FileName: ReplyMsg.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午11:06:08   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: ReplyMsg   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午11:06:08     
 */
public class ReplyMsg extends BaseMsg {
    public ReplyMsg() {
        super();
        setType(MsgType.REPLY);
    }

    private ReplyBody body;

    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
