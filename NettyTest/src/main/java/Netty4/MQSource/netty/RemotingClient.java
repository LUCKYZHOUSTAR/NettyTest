package Netty4.MQSource.netty;

import java.util.List;
import java.util.concurrent.ExecutorService;

import Netty4.MQSource.RemotingCommand;
import Netty4.MQSource.exception.RemotingConnectException;
import Netty4.MQSource.exception.RemotingSendRequestException;
import Netty4.MQSource.exception.RemotingTimeoutException;
import Netty4.MQSource.exception.RemotingTooMuchRequestException;

public interface RemotingClient {
    public void updateNameServerAddressList(final List<String> addrs);

    public List<String> getNameServerAddressList();

    public RemotingCommand invokeSync(final String addr, final RemotingCommand request,
                                      final long timeoutMillis) throws InterruptedException,
                                                               RemotingConnectException,
                                                               RemotingSendRequestException,
                                                               RemotingTimeoutException;

//    public void invokeAsync(final String addr, final RemotingCommand request,
//                            final long timeoutMillis, final InvokeCallback invokeCallback)
//                                                                                          throws InterruptedException,
//                                                                                          RemotingConnectException,
//                                                                                          RemotingTooMuchRequestException,
//                                                                                          RemotingTimeoutException,
//                                                                                          RemotingSendRequestException;

    public void invokeOneway(final String addr, final RemotingCommand request,
                             final long timeoutMillis) throws InterruptedException,
                                                      RemotingConnectException,
                                                      RemotingTooMuchRequestException,
                                                      RemotingTimeoutException,
                                                      RemotingSendRequestException;

//    public void registerProcessor(final int requestCode, final NettyRequestProcessor processor,
//                                  final ExecutorService executor);

    public boolean isChannelWriteable(final String addr);
}
