/**     
 * @FileName: TcpClientHandler.java   
 * @Package:Netty4.example   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月12日 下午3:35:32   
 * @version V1.0     
 */
package Netty4.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**  
 * @ClassName: TcpClientHandler   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月12日 下午3:35:32     
 */
public class TcpClientHandler extends ChannelInboundHandlerAdapter{

    
//    private final ByteBuf firstMessage;
    private String msgResult;
    final BlockingQueue<String> answer = new LinkedBlockingQueue<String>();
    
    /**   
     * @return answer   
     */
    public BlockingQueue<String> getAnswer() {
        return answer;
    }

    /**   
     * @return msgResult   
     */
    public String getMsgResult() {
        return msgResult;
    }

    /**     
     * @param msgResult the msgResult to set     
     */
    public void setMsgResult(String msgResult) {
        this.msgResult = msgResult;
    }

    public TcpClientHandler(){
//        byte[] req="QUERY TIME ORDER".getBytes();
//        firstMessage=Unpooled.buffer(req.length);
//        firstMessage.writeBytes(req);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(firstMessage);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @param msg
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelRead(io.netty.channel.ChannelHandlerContext, java.lang.Object)   
     */  
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf=(ByteBuf) msg;
        byte[] req=new byte[buf.readableBytes()];
        
        buf.readBytes(req);
        final String body=new String(req,"UTF-8");
        ctx.channel().close().addListener(new ChannelFutureListener() {

            public void operationComplete(ChannelFuture arg0) throws Exception {
                //LinkedBlockingQueue不接受null。
                //对于put方法，若向队尾添加元素的时候发现队列已经满了会发生阻塞一直等待空间，以加入元素。
                answer.offer(body);
            }

        });
        this.msgResult=body;
//        System.out.println(body);
    }

    /* (non-Javadoc)   
     * @param ctx
     * @throws Exception   
     * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)   
     */  
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("我已经读取完毕了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
    
    
    
    
}
