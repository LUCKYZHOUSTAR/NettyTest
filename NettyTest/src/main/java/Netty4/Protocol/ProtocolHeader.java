/**     
 * @FileName: ProtocolHeader.java   
 * @Package:Netty4.Protocol   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 上午9:55:46   
 * @version V1.0     
 */
package Netty4.Protocol;

/**  
 * @ClassName: ProtocolHeader   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 上午9:55:46     
 */
public class ProtocolHeader {

    private byte  magic;    //帧数
    private byte  msgType;  //消息类型
    private short reserver; //保留字
    private short sn;       //序列号
    private int   len;      //消息体的长度

    public byte getMagic() {
        return magic;
    }

    public void setMagic(byte magic) {
        this.magic = magic;
    }

    public byte getMsgType() {
        return msgType;
    }

    public void setMsgType(byte msgType) {
        this.msgType = msgType;
    }

    public short getReserve() {
        return reserver;
    }

    public void setReserve(short reserve) {
        this.reserver = reserve;
    }

    public short getSn() {
        return sn;
    }

    public void setSn(short sn) {
        this.sn = sn;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public ProtocolHeader() {
    }

    /**
     * 
     */
    public ProtocolHeader(byte magic, byte msgType, short reserve, short sn, int len) {
        this.magic = magic;
        this.msgType = msgType;
        this.reserver = reserve;
        this.sn = sn;
        this.len = len;
    }

}
