/**     
 * @FileName: RemotingSerializable.java   
 * @Package:Netty4.MQSource.Protocl   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月21日 下午5:54:24   
 * @version V1.0     
 */
package Netty4.MQSource.Protocl;

import java.nio.charset.Charset;

import com.lucky.fastjson.JSON;

/**  
 * @ClassName: RemotingSerializable   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月21日 下午5:54:24     
 */
public class RemotingSerializable {

    public String toJson() {
        return toJson(false);
    }

    public String toJson(final boolean prettyFormat) {
        return toJson(this, prettyFormat);
    }

    public static String toJson(final Object obj, boolean prettyFormat) {
        return JSON.toJSONString(obj, prettyFormat);
    }

    public static <T> T fromJson(String json, Class<T> classOft) {
        return JSON.parseObject(json, classOft);
    }

    public byte[] encode() {
        final String json = this.toJson();
        System.out.println(json);
        if (json != null) {
            return json.getBytes();
        }

        return null;
    }

    public static byte[] encode(final Object obj) {
        final String json = toJson(obj, false);
        if (json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }

        return null;
    }

    public static <T> T Decoder(final byte[] data, Class<T> classOft) {
        final String json = new String(data, Charset.forName("UTF-8"));
        return fromJson(json, classOft);
    }
}
