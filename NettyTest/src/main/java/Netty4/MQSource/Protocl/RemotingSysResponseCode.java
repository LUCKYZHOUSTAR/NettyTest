/**     
 * @FileName: RemotingSysResponseCode.java   
 * @Package:Netty4.MQSource.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月21日 下午5:22:15   
 * @version V1.0     
 */
package Netty4.MQSource.Protocl;


/**  
 * @ClassName: RemotingSysResponseCode   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月21日 下午5:22:15     
 */
public class RemotingSysResponseCode {

    //成功
    public static final int SUCCESS                    = 0;
    //发生了未知的异常
    public static final int SYSTEM_ERROR               = 1;
    //由于线程池I拥堵，系统繁忙
    public static final int SYSTEM_BUSY                = 2;
    //请求代码不支持
    public static final int REQUEST_CODE_NOT_SUPPORTED = 3;
    //事务失败，添加db失败
    public static final int TRANSACTION_FAILED         = 4;
}
