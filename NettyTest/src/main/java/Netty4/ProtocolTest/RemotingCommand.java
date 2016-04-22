/**     
 * @FileName: RemotingCommand.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:38:45   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

/**  
 * @ClassName: RemotingCommand   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:38:45     
 */
public class RemotingCommand {

    public static String        RemotingVersionKey = "com.test.Server.version";

    private static volatile int ConfigVersion      = -1;

    //命令序列号
    private int                 opaque             = 0;

    private int                 version            = 0;

    private RemotingCommandType type;
    //消息体
    private transient byte[]    body;

    public enum RemotingCommandType {
        REQUEST_COMMAND, RESPONSE_COMMAND;
    }

    public static RemotingCommand createRequestCommand() {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.REQUEST_COMMAND);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(byte[] body) {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        cmd.setBody(body);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(int code, String mark) {
        RemotingCommand cmd = new RemotingCommand();
        String str = "error[" + code + "]" + mark;
        cmd.setBody(str.getBytes());
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        setCmdVersion(cmd);
        return cmd;
    }

    private static void setCmdVersion(RemotingCommand cmd) {
        if (ConfigVersion >= 0) {
            cmd.setVersion(ConfigVersion);
        } else {
            String v = System.getProperty(RemotingVersionKey);
            if (v != null) {
                int value = Integer.parseInt(v);
                cmd.setVersion(value);
                ConfigVersion = value;
            }
        }
    }

    /* (non-Javadoc)   
     * @return   
     * @see java.lang.Object#toString()   
     */
    @Override
    public String toString() {
        return "RemotingCommand [opaque=" + opaque + ", version=" + version + ", type=" + type
               + "]";
    }

    /**   
     * @return opaque   
     */
    public int getOpaque() {
        return opaque;
    }

    /**     
     * @param opaque the opaque to set     
     */
    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }

    /**   
     * @return version   
     */
    public int getVersion() {
        return version;
    }

    /**     
     * @param version the version to set     
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**   
     * @return type   
     */
    public RemotingCommandType getType() {
        return type;
    }

    /**     
     * @param type the type to set     
     */
    public void setType(RemotingCommandType type) {
        this.type = type;
    }

    /**   
     * @return body   
     */
    public byte[] getBody() {
        return body;
    }

    /**     
     * @param body the body to set     
     */
    public void setBody(byte[] body) {
        this.body = body;
    }

}
