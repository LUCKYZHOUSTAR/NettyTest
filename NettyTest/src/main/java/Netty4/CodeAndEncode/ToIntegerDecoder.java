/**     
 * @FileName: ToIntegerDecoder.java   
 * @Package:Netty4.CodeAndEncode   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月13日 上午10:51:12   
 * @version V1.0     
 */
package Netty4.CodeAndEncode;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**  
 * @ClassName: ToIntegerDecoder   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月13日 上午10:51:12     
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf,
                          List<Object> list) throws Exception {

        
        if (bytebuf.readableBytes() > 4) {
            list.add(bytebuf.readInt());
        }
    }

}
