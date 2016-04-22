/**     
 * @FileName: LoginMsg.java   
 * @Package:Netty4.firstTest.Heart.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午11:03:25   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Protocl;

/**  
 * @ClassName: LoginMsg   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午11:03:25     
 */
public class LoginMsg extends BaseMsg{
    private String userName;
    private String password;

    public LoginMsg() {
        super();
        setType(MsgType.LOGIN);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
