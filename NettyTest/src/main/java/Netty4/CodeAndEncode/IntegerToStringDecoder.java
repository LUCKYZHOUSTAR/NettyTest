/**     
 * @FileName: IntegerToStringDecoder.java   
 * @Package:Netty4.CodeAndEncode   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月13日 上午10:56:35   
 * @version V1.0     
 */
package Netty4.CodeAndEncode;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**  
 * @ClassName: IntegerToStringDecoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月13日 上午10:56:35     
 */
public class IntegerToStringDecoder extends MessageToMessageDecoder<Integer>{

    @Override
    protected void decode(ChannelHandlerContext arg0, Integer integer, List<Object> list)
                                                                                      throws Exception {
       list.add(String.valueOf(integer));
    }

}
