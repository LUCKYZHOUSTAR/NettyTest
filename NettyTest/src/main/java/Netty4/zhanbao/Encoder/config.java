/**     
 * @FileName: config.java   
 * @Package:Netty4.zhanbao.Encoder   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月14日 上午11:00:16   
 * @version V1.0     
 */
package Netty4.zhanbao.Encoder;

/**  
 * @ClassName: config   
 * @Description: 
 * @author: LUCKY  
 * @date:2016年4月14日 上午11:00:16     
 */
public class config {

    private int Port=8888;
    private int WorkerThreads=8;
    private int SelectorThreads=3;
    private int ChannelMaxIdleTimeSeconds=120;
    private int socketSendBufSize=Integer.parseInt(System.getProperty("sendbuf", "65535"));
    private int socketRecevBufSize=Integer.parseInt(System.getProperty("recevbuf", "65535"));
    /**   
     * @return port   
     */
    public int getPort() {
        return Port;
    }
    /**     
     * @param port the port to set     
     */
    public void setPort(int port) {
        Port = port;
    }
    /**   
     * @return workerThreads   
     */
    public int getWorkerThreads() {
        return WorkerThreads;
    }
    /**     
     * @param workerThreads the workerThreads to set     
     */
    public void setWorkerThreads(int workerThreads) {
        WorkerThreads = workerThreads;
    }
    /**   
     * @return selectorThreads   
     */
    public int getSelectorThreads() {
        return SelectorThreads;
    }
    /**     
     * @param selectorThreads the selectorThreads to set     
     */
    public void setSelectorThreads(int selectorThreads) {
        SelectorThreads = selectorThreads;
    }
    /**   
     * @return channelMaxIdleTimeSeconds   
     */
    public int getChannelMaxIdleTimeSeconds() {
        return ChannelMaxIdleTimeSeconds;
    }
    /**     
     * @param channelMaxIdleTimeSeconds the channelMaxIdleTimeSeconds to set     
     */
    public void setChannelMaxIdleTimeSeconds(int channelMaxIdleTimeSeconds) {
        ChannelMaxIdleTimeSeconds = channelMaxIdleTimeSeconds;
    }
    /**   
     * @return socketSendBufSize   
     */
    public int getSocketSendBufSize() {
        return socketSendBufSize;
    }
    /**     
     * @param socketSendBufSize the socketSendBufSize to set     
     */
    public void setSocketSendBufSize(int socketSendBufSize) {
        this.socketSendBufSize = socketSendBufSize;
    }
    /**   
     * @return socketRecevBufSize   
     */
    public int getSocketRecevBufSize() {
        return socketRecevBufSize;
    }
    /**     
     * @param socketRecevBufSize the socketRecevBufSize to set     
     */
    public void setSocketRecevBufSize(int socketRecevBufSize) {
        this.socketRecevBufSize = socketRecevBufSize;
    }
    
}
