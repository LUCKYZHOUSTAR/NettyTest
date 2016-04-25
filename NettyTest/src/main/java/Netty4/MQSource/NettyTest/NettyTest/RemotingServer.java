package Netty4.MQSource.NettyTest.NettyTest;

import java.util.concurrent.ExecutorService;

import org.omg.CORBA.PUBLIC_MEMBER;



/**
 * @author LUCKY
 *
 */
public interface RemotingServer {

    
    public void registerProcessor(final NettyRequestProcessor processor,final ExecutorService executorService);
    
    
    public void start();
    
    
    public void shutDown();
    
    public int localListenPort();
}
