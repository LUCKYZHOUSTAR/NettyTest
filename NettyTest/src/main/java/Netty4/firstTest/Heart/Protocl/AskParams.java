/**     
 * @FileName: AskParams.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午11:01:46   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

import java.io.Serializable;

/**  
 * @ClassName: AskParams   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午11:01:46     
 */
public class AskParams implements Serializable{

    /**   
     * @Fields: serialVersionUID  
     * @Desc: 
     */ 
    private static final long serialVersionUID = -2701999687172399351L;

    
    private String auth;


    /**   
     * @return auth   
     */
    public String getAuth() {
        return auth;
    }


    /**     
     * @param auth the auth to set     
     */
    public void setAuth(String auth) {
        this.auth = auth;
    }
    
    
    
}
