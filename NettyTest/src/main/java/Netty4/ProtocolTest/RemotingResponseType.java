/**     
 * @FileName: RemotingCommandType.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:41:29   
 * @version V1.0     
 */
package Netty4.ProtocolTest;


/**  
 * @ClassName: RemotingCommandType   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:41:29     
 */
public class RemotingResponseType {

    //成功
    public static final int SUCCESS=0;
    //发生了未捕获的异常
    public static final int SYSTEM_ERROR=1;
    //由于线程池堵塞，系统繁忙
    public static final int SYSTEM_BUSY=2;
    //请求代码不支持
    public static final int REQUEST_CODE_NOT_SUPPORTED=3;
    //事务失败，添加DB失败
    public static final int TRANSACTION_FAILED=4;
}
