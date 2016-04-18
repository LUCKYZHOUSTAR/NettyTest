/**     
 * @FileName: ByteToByteBuffer.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午11:55:49   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.ByteBuffer;
import java.util.List;

/**  
 * @ClassName: ByteToByteBuffer   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 上午11:55:49     
 */
public class ByteToByteBuffer extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // TODO Auto-generated method stub
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        byte[] tempData = new byte[in.readableBytes()];
        in.readBytes(tempData);
        ByteBuffer buffer = ByteBuffer.wrap(tempData);
        return buffer;

    }

}
