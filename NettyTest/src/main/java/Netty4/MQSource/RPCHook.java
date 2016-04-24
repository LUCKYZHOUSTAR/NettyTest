package Netty4.MQSource;

public interface RPCHook {

    public void doBeforeRequest(final String remoteAddr, final RemotingCommand request);

    public void doAfterResponse(final String remoteAddr, final RemotingCommand request,
                                final RemotingCommand response);
}
