/**     
 * @FileName: MsgType.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 上午9:59:53   
 * @version V1.0     
 */
package Netty4.Protocol;

/**  
 * @ClassName: MsgType   
 * @Description: 消息的类型
 * @author: LUCKY  
 * @date:2016年4月18日 上午9:59:53     
 */
public enum MsgType {
    EMGW_LOGIN_REQ((byte) 0x00),
    EMGW_LOGIN_RES((byte) 0x01);

    private byte value;

    /**   
     * @return value   
     */
    public byte getValue() {
        return value;
    }

    /**     
     * @param value the value to set     
     */
    public void setValue(byte value) {
        this.value = value;
    }

    private MsgType(byte value) {
        this.value = value;
    }

}
