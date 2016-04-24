package Netty4.MQSource.netty;

//Netty客户端配置
public class NettyClientConfig {

    /**
     * Worker thread number
     */
    private int     clientWorkerThreads                = 4;
    private int     clientCallbackExecutorThreads      = Runtime.getRuntime().availableProcessors();
    private int     clientOneWaySemaphoreValue         = 2048;
    private int     clientAsyncSemaphoreValue          = 2048;
    private long    connectTimeOutMills                = 3000;
    private long    channelNotActiveInterval           = 1000 * 60;

    /**
     * IdleStateEvent will be triggered when neither read nor write was performed for
     * the specified period of this time. Specify {@code 0} to disable
     */
    private int     clientChannelMaxIdleTimeSeconds    = 120;
    private int     clientSocketSndBufSize             = 65535;
    private int     clientSocketRcvBufSize             = 65535;
    private boolean clientPooledByteBufAllocatorEnable = false;

    public int getClientWorkerThreads() {
        return clientWorkerThreads;
    }

    public void setClientWorkerThreads(int clientWorkerThreads) {
        this.clientWorkerThreads = clientWorkerThreads;
    }

    public int getClientCallbackExecutorThreads() {
        return clientCallbackExecutorThreads;
    }

    public void setClientCallbackExecutorThreads(int clientCallbackExecutorThreads) {
        this.clientCallbackExecutorThreads = clientCallbackExecutorThreads;
    }

    public int getClientOneWaySemaphoreValue() {
        return clientOneWaySemaphoreValue;
    }

    public void setClientOneWaySemaphoreValue(int clientOneWaySemaphoreValue) {
        this.clientOneWaySemaphoreValue = clientOneWaySemaphoreValue;
    }

    public int getClientAsyncSemaphoreValue() {
        return clientAsyncSemaphoreValue;
    }

    public void setClientAsyncSemaphoreValue(int clientAsyncSemaphoreValue) {
        this.clientAsyncSemaphoreValue = clientAsyncSemaphoreValue;
    }

    public long getConnectTimeOutMills() {
        return connectTimeOutMills;
    }

    public void setConnectTimeOutMills(long connectTimeOutMills) {
        this.connectTimeOutMills = connectTimeOutMills;
    }

    public long getChannelNotActiveInterval() {
        return channelNotActiveInterval;
    }

    public void setChannelNotActiveInterval(long channelNotActiveInterval) {
        this.channelNotActiveInterval = channelNotActiveInterval;
    }

    public int getClientChannelMaxIdleTimeSeconds() {
        return clientChannelMaxIdleTimeSeconds;
    }

    public void setClientChannelMaxIdleTimeSeconds(int clientChannelMaxIdleTimeSeconds) {
        this.clientChannelMaxIdleTimeSeconds = clientChannelMaxIdleTimeSeconds;
    }

    public int getClientSocketSndBufSize() {
        return clientSocketSndBufSize;
    }

    public void setClientSocketSndBufSize(int clientSocketSndBufSize) {
        this.clientSocketSndBufSize = clientSocketSndBufSize;
    }

    public int getClientSocketRcvBufSize() {
        return clientSocketRcvBufSize;
    }

    public void setClientSocketRcvBufSize(int clientSocketRcvBufSize) {
        this.clientSocketRcvBufSize = clientSocketRcvBufSize;
    }

    public boolean isClientPooledByteBufAllocatorEnable() {
        return clientPooledByteBufAllocatorEnable;
    }

    public void setClientPooledByteBufAllocatorEnable(boolean clientPooledByteBufAllocatorEnable) {
        this.clientPooledByteBufAllocatorEnable = clientPooledByteBufAllocatorEnable;
    }

}
