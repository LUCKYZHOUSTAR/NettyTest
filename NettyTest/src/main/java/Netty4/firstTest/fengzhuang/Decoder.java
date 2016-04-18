/**     
 * @FileName: Encoder.java   
 * @Package:Netty4.firstTest.fengzhuang   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月15日 下午3:13:05   
 * @version V1.0     
 */
package Netty4.firstTest.fengzhuang;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.nio.ByteOrder;

/**  
 * @ClassName: Encoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月15日 下午3:13:05     
 */
public class Decoder extends LengthFieldBasedFrameDecoder {
    public Decoder() {
        super(65535, 0, 8, 0, 8);
    }

    @Override
    protected long getUnadjustedFrameLength(ByteBuf buf, int offset, int length, ByteOrder order) {
        buf = buf.order(order);
        byte[] lendata = new byte[8];
        //一个字节等于8位
        buf.getBytes(0, lendata);
        int frameLength = Integer.parseInt(new String(lendata));
        return frameLength;
    }

}
