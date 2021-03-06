/**     
 * @FileName: Pair.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午1:44:31   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

/**  
 * @ClassName: Pair   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午1:44:31     
 */
public class Pair<T1,T2>{

    private T1 object1;
    private T2 object2;
    public Pair(T1 object1, T2 object2) {
        this.object1 = object1;
        this.object2 = object2;
    }
    /**   
     * @return object1   
     */
    public T1 getObject1() {
        return object1;
    }
    /**     
     * @param object1 the object1 to set     
     */
    public void setObject1(T1 object1) {
        this.object1 = object1;
    }
    /**   
     * @return object2   
     */
    public T2 getObject2() {
        return object2;
    }
    /**     
     * @param object2 the object2 to set     
     */
    public void setObject2(T2 object2) {
        this.object2 = object2;
    }
    
    
}
