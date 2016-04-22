/**     
 * @FileName: RequestProccessor.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午1:46:26   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

import io.netty.channel.ChannelHandlerContext;

/**  
 * @ClassName: RequestProccessor   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月18日 下午1:46:26     
 */
public interface RequestProccessor {

    RemotingCommand processRequest(ChannelHandlerContext ctx,RemotingCommand request) throws Exception;
}
