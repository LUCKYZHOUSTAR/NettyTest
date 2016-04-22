/**     
 * @FileName: ConfigTest.java   
 * @Package:Netty4.ProtocolTest   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月18日 下午12:30:29   
 * @version V1.0     
 */
package Netty4.ProtocolTest;

/**  
 * @ClassName: ConfigTest   
 * @Description: Netty服务端相关参数的配置信息
 * @author: LUCKY  
 * @date:2016年4月18日 下午12:30:29     
 */
public class ConfigTest {

    private int listenPort                      = 8888;
    private int serverWorkerThreads             = 8;
    private int serverSelectorThreads           = 3;
    private int serverChannelMaxIdelTimeSeconds = 120;
    private int serverSocketSndBufSize          = Integer.parseInt(System.getProperty(
                                                    "test.netty.SndBufSize", "65535"));
    private int serverSocketRecBufSize          = Integer.parseInt(System.getProperty(
                                                    "test.netty.RecBufSize", "65535"));

    /**   
     * @return listenPort   
     */
    public int getListenPort() {
        return listenPort;
    }

    /**     
     * @param listenPort the listenPort to set     
     */
    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    /**   
     * @return serverWorkerThreads   
     */
    public int getServerWorkerThreads() {
        return serverWorkerThreads;
    }

    /**     
     * @param serverWorkerThreads the serverWorkerThreads to set     
     */
    public void setServerWorkerThreads(int serverWorkerThreads) {
        this.serverWorkerThreads = serverWorkerThreads;
    }

    /**   
     * @return serverSelectorThreads   
     */
    public int getServerSelectorThreads() {
        return serverSelectorThreads;
    }

    /**     
     * @param serverSelectorThreads the serverSelectorThreads to set     
     */
    public void setServerSelectorThreads(int serverSelectorThreads) {
        this.serverSelectorThreads = serverSelectorThreads;
    }

    /**   
     * @return serverChannelMaxIdelTimeSeconds   
     */
    public int getServerChannelMaxIdelTimeSeconds() {
        return serverChannelMaxIdelTimeSeconds;
    }

    /**     
     * @param serverChannelMaxIdelTimeSeconds the serverChannelMaxIdelTimeSeconds to set     
     */
    public void setServerChannelMaxIdelTimeSeconds(int serverChannelMaxIdelTimeSeconds) {
        this.serverChannelMaxIdelTimeSeconds = serverChannelMaxIdelTimeSeconds;
    }

    /**   
     * @return serverSocketSndBufSize   
     */
    public int getServerSocketSndBufSize() {
        return serverSocketSndBufSize;
    }

    /**     
     * @param serverSocketSndBufSize the serverSocketSndBufSize to set     
     */
    public void setServerSocketSndBufSize(int serverSocketSndBufSize) {
        this.serverSocketSndBufSize = serverSocketSndBufSize;
    }

    /**   
     * @return serverSocketRecBufSize   
     */
    public int getServerSocketRecBufSize() {
        return serverSocketRecBufSize;
    }

    /**     
     * @param serverSocketRecBufSize the serverSocketRecBufSize to set     
     */
    public void setServerSocketRecBufSize(int serverSocketRecBufSize) {
        this.serverSocketRecBufSize = serverSocketRecBufSize;
    }

}
