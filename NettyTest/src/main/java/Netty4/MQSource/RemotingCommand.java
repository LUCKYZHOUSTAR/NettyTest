/**     
 * @FileName: RemotingCommand.java   
 * @Package:Netty4.MQSource   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月21日 下午4:14:13   
 * @version V1.0     
 */
package Netty4.MQSource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.rocketmq.remoting.protocol.RemotingSerializable;

import Netty4.MQSource.Protocl.RemotingSysResponseCode;

/**  
 * @ClassName: RemotingCommand   
 * @Description:Remoting模块中，服务器与客户端通过传递Remotingcommand来交互 
 * @author: LUCKY  
 * @date:2016年4月21日 下午4:14:13     
 */
public class RemotingCommand {

    public static String         RemotingVersionKey = "rocketmq.remoting.version";

    //一般用在一改多读的情况小，保证主内存区一致
    private static volatile int  ConfigVersion      = -1;

    private static AtomicInteger RequestId          = new AtomicInteger(0);
    //0 REQUEST_COMMAND  1:RESPONSE_COMMAND
    private static final int     RPC_TYPE           = 0;

    private static final int     RPC_ONEWAY         = 1;                          //0,PRC
    //1,OneWay

    //Header部分
    private int                  code;
    private LanguageCode         language           = LanguageCode.JAVA;

    public enum LanguageCode {
        JAVA, CPP, DOTNET, PYTHON, DELPHI, ERLANG, RUBY, OTHER, HTTP,
    }

    private int                           version = 0;
    private int                           opaque  = RequestId.getAndIncrement();

    private int                           flag    = 0;
    private String                        remark;
    private HashMap<String, String>       extsFields;
    private transient CommandCustomHeader customerHeader;

    /*
     * Body部分
     */
    private transient byte[]              body;

    protected RemotingCommand() {

    }

    public static RemotingCommand createRequestCommand(int code, CommandCustomHeader customHeader) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        cmd.setCustomerHeader(customHeader);
        setCmdVersion(cmd);
        return cmd;
    }

    public static RemotingCommand createResponseCommand(Class<? extends CommandCustomHeader> classHeader) {

        RemotingCommand cmd = createResponseCommand(RemotingSysResponseCode.SYSTEM_ERROR,
            "not set any response code", classHeader);

        return cmd;
    }

    public static RemotingCommand createResponseCommand(int code, String remark) {
        return createResponseCommand(code, remark, null);
    }

    public static RemotingCommand createResponseCommand(int code,
                                                        String remark,
                                                        Class<? extends CommandCustomHeader> classHeader) {
        RemotingCommand cmd = new RemotingCommand();
        cmd.setCode(code);
        cmd.setRemark(remark);
        setCmdVersion(cmd);
        if (classHeader != null) {
            try {
                CommandCustomHeader objectHeader = classHeader.newInstance();
                cmd.setCustomerHeader(objectHeader);
            } catch (Exception e) {
            }
        }
        return cmd;
    }

    public void makeCustomHeaderToNet() {
        if (this.customerHeader != null) {
            Field[] fields = this.customerHeader.getClass().getDeclaredFields();
            if (null == this.extsFields) {
                this.extsFields = new HashMap<String, String>();
            }

            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    String name = field.getName();
                    if (!name.startsWith("this")) {
                        Object value = null;
                        try {
                            field.setAccessible(true);
                            value = field.get(this.customerHeader);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }

                        if (value != null) {
                            this.extsFields.put(name, value.toString());
                        }
                    }
                }
            }
        }
    }

    public CommandCustomHeader readCustomHeader() {
        return customerHeader;
    }

    public void writeCustomHeader(CommandCustomHeader customHeader) {
        this.customerHeader = customHeader;
    }

    public static void main(String[] args) {
        System.out.println(Double.class.getCanonicalName());
        System.out.println(double.class.getCanonicalName());
    }

    private static void setCmdVersion(RemotingCommand cmd) {
        if (ConfigVersion >= 0) {
            cmd.setVersion(ConfigVersion);
        } else {
            String v = System.getenv(RemotingVersionKey);
            if (v != null) {
                int value = Integer.parseInt(v);
                cmd.setVersion(value);
                ConfigVersion = value;
            }
        }
    }

    private byte[] buildHeader() {
        this.makeCustomHeaderToNet();
        return RemotingSerializable.encode(this);
    }

    public ByteBuffer encode() {
        //1个字节 header lengthsize
        int length = 4;
        //header data length
        ByteBuffer result = ByteBuffer.allocate(4 + length);
        //length
        result.putInt(length);

    }

    /**   
     * @return version   
     */
    public int getVersion() {
        return version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**     
     * @param version the version to set     
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**   
     * @return code   
     */
    public int getCode() {
        return code;
    }

    /**     
     * @param code the code to set     
     */
    public void setCode(int code) {
        this.code = code;
    }

    /**   
     * @return customerHeader   
     */
    public CommandCustomHeader getCustomerHeader() {
        return customerHeader;
    }

    /**     
     * @param customerHeader the customerHeader to set     
     */
    public void setCustomerHeader(CommandCustomHeader customerHeader) {
        this.customerHeader = customerHeader;
    }

    public void markResponseType() {
        int bits = 1 << RPC_TYPE;
        this.flag |= bits;
    }

    public boolean isOnewayRPC() {
        int bits = 1 << RPC_ONEWAY;
        return (this.flag & bits) == bits;
    }

    public LanguageCode getLanguage() {
        return language;
    }

    public void setLanguage(LanguageCode language) {
        this.language = language;
    }

}
