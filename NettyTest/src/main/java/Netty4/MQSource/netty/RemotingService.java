package Netty4.MQSource.netty;

import Netty4.MQSource.RPCHook;

public interface RemotingService {

    
    public void start();
    public void shutDown();
    public void registerRPCHook(RPCHook rpcHook);
}
