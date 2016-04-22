/**     
 * @FileName: NettyServerHandler.java   
 * @Package:Netty4.firstTest.Heart.Server   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月19日 上午10:55:06   
 * @version V1.0     
 */
package Netty4.firstTest.Heart.Server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;
import Netty4.firstTest.Heart.Protocl.AskMsg;
import Netty4.firstTest.Heart.Protocl.BaseMsg;
import Netty4.firstTest.Heart.Protocl.LoginMsg;
import Netty4.firstTest.Heart.Protocl.MsgType;
import Netty4.firstTest.Heart.Protocl.PingMsg;
import Netty4.firstTest.Heart.Protocl.ReplyClientBody;
import Netty4.firstTest.Heart.Protocl.ReplyMsg;
import Netty4.firstTest.Heart.Protocl.ReplyServerBody;

/**  
 * @ClassName: NettyServerHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月19日 上午10:55:06     
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseMsg msg) throws Exception {
        // TODO Auto-generated method stub

        if (MsgType.LOGIN.equals(msg.getType())) {
            LoginMsg loginMsg = (LoginMsg) msg;
            if ("robin".equals(loginMsg.getUserName()) && "yao".equals(loginMsg.getPassword())) {
                //登录成功，把channel存储到服务端的map中
                NettyChannelMap.add(loginMsg.getClientId(), (SocketChannel) ctx.channel());
            }
        } else {
            if (NettyChannelMap.get(msg.getClientId()) == null) {
                //说明未登录，或者链接断了，服务端向客户端发起登录请求，让客户端重新登录
                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                LoginMsg loginMsg = new LoginMsg();
                ctx.channel().writeAndFlush(loginMsg);
            }
        }

        switch (msg.getType()) {
            case PING: {
                PingMsg pingMsg = (PingMsg) msg;
                PingMsg replyPing = new PingMsg();
                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(replyPing);
            }
            break;
            case ASK:{
                //收到客户端的请求
                AskMsg askMsg=(AskMsg)msg;
                if("authToken".equals(askMsg.getParams().getAuth())){
                    ReplyServerBody replyBody=new ReplyServerBody("server info $$$$ !!!");
                    ReplyMsg replyMsg=new ReplyMsg();
                    replyMsg.setBody(replyBody);
                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(replyMsg);
                }
            }break;
            case REPLY:{
                //收到客户端回复
                ReplyMsg replyMsg=(ReplyMsg)msg;
                ReplyClientBody clientBody=(ReplyClientBody)replyMsg.getBody();
                System.out.println("receive client msg: "+clientBody.getClientInfo());
            }break;
            default:break;
            
            
        }

        
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyChannelMap.remove((SocketChannel) ctx.channel());
    }

}
