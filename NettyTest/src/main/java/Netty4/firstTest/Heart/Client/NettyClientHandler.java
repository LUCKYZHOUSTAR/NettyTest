/**     
 * @FileName: NettyClientHandler.java   
 * @Package:Netty4.firstTest.Heart.Client   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 下午12:51:15   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import Netty4.firstTest.Heart.Protocl.BaseMsg;
import Netty4.firstTest.Heart.Protocl.LoginMsg;
import Netty4.firstTest.Heart.Protocl.MsgType;
import Netty4.firstTest.Heart.Protocl.PingMsg;
import Netty4.firstTest.Heart.Protocl.ReplyClientBody;
import Netty4.firstTest.Heart.Protocl.ReplyMsg;
import Netty4.firstTest.Heart.Protocl.ReplyServerBody;

/**  
 * @ClassName: NettyClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 下午12:51:15     
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<BaseMsg>{
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    PingMsg pingMsg = new PingMsg();
                    ctx.writeAndFlush(pingMsg);
                    System.out.println("send ping to server----------");
                    break;
                default:
                    break;
            }
        }
    }


    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */  
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg baseMsg) throws Exception {
        MsgType msgType = baseMsg.getType();
        switch (msgType) {
            case LOGIN: {
                //向服务器发起登录
                LoginMsg loginMsg = new LoginMsg();
                loginMsg.setPassword("yao");
                loginMsg.setUserName("robin");
                ctx.writeAndFlush(loginMsg);
            }
                break;
            case PING: {
                System.out.println("receive ping from server----------");
            }
                break;
            case ASK: {
                ReplyClientBody replyClientBody = new ReplyClientBody("client info **** !!!");
                ReplyMsg replyMsg = new ReplyMsg();
                replyMsg.setBody(replyClientBody);
                ctx.writeAndFlush(replyMsg);
            }
                break;
            case REPLY: {
                ReplyMsg replyMsg = (ReplyMsg) baseMsg;
                ReplyServerBody replyServerBody = (ReplyServerBody) replyMsg.getBody();
                System.out.println("receive client msg: " + replyServerBody.getServerInfo());
            }
            default:
                break;
        }
        ReferenceCountUtil.release(msgType);        
    }
}
