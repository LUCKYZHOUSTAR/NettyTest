/**     
 * @FileName: BigIntegerDecoder.java   
 * @Package:Netty4.book.factorial   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月13日 下午2:55:51   
 * @version V1.0     
 */
package Netty4.book.factorial;

import java.math.BigInteger;
import java.util.List;

import javassist.expr.NewArray;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

/**  
 * @ClassName: BigIntegerDecoder   
 * @Description: Decodes the binary representation of a {@link BigInteger} prepended
 * with a magic number ('F' or 0x46) and a 32-bit integer length prefix into a
 * {@link BigInteger} instance.  For example, { 'F', 0, 0, 0, 1, 42 } will be
 * decoded into new BigInteger("42").
 * @author: LUCKY  
 * @date:2016年4月13日 下午2:55:51     
 */
public class BigIntegerDecoder extends ByteToMessageDecoder{

    /* (non-Javadoc)   
     * @param channelhandlercontext
     * @param bytebuf
     * @param list
     * @throws Exception   
     * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)   
     */  
    @Override
    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf,
                          List<Object> list) throws Exception {
        //wait until the length prefix is available
        //一个F的字节，一个data长度int的四个字节，所以一定要大于5
        if(bytebuf.readableBytes()<5){
            return;
        }
        
        bytebuf.markReaderIndex();
        //Check the magic number
        int magicNumber=bytebuf.readUnsignedByte();
        
        if(magicNumber!='F'){
            bytebuf.resetReaderIndex();
            throw new CorruptedFrameException("Invalid magic number: " + magicNumber);
        }
        
        // Wait until the whole data is available.
        //数据的长度
        int dataLength=bytebuf.readInt();
        if(bytebuf.readableBytes()<dataLength){
            bytebuf.resetReaderIndex();
            return;
        }
        
        
        //onvert the received data into a new BigInteger.
        byte[] decoded=new byte[dataLength];
        bytebuf.readBytes(decoded);
        
        list.add(new BigInteger(decoded));
    }

}
