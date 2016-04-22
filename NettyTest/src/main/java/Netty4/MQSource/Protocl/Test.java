/**     
 * @FileName: Test.java   
 * @Package:Netty4.MQSource.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月21日 下午7:32:02   
 * @version V1.0     
 */
package Netty4.MQSource.Protocl;

/**  
 * @ClassName: Test   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月21日 下午7:32:02     
 */
public class Test {

    private String name;
    private String pwd;

    /**   
     * @return name   
     */
    public String getName() {
        return name;
    }

    /**     
     * @param name the name to set     
     */
    public void setName(String name) {
        this.name = name;
    }

    /**   
     * @return pwd   
     */
    public String getPwd() {
        return pwd;
    }

    /**     
     * @param pwd the pwd to set     
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.setName("张三");
        test.setPwd("李四");
        System.out.println(RemotingSerializable.toJson(test, false));
    }
}
