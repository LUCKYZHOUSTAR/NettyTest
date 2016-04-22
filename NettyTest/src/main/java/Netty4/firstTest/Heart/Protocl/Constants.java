/**     
 * @FileName: Constants.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:59:20   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: Constants   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:59:20     
 */
public class Constants {

    private static String clientId;

    /**   
     * @return clientId   
     */
    public static String getClientId() {
        return clientId;
    }

    /**     
     * @param clientId the clientId to set     
     */
    public static void setClientId(String clientId) {
        Constants.clientId = clientId;
    }
    
    
}
