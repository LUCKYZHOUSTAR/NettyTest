/**     
 * @FileName: ReplyServerBody.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午11:06:32   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: ReplyServerBody   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午11:06:32     
 */
public class ReplyServerBody extends ReplyBody {
    private String serverInfo;

    public ReplyServerBody(String serverInfo) {
        this.serverInfo = serverInfo;
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public void setServerInfo(String serverInfo) {
        this.serverInfo = serverInfo;
    }
}
