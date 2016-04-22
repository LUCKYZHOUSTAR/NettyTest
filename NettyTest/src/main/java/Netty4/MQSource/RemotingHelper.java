/**     
 * @FileName: RemotingHelperr.java   
 * @Package:Netty4.MQSource   
 * @Description: 
 * @author: LUCKY    
 * @date:2016年4月21日 下午3:02:34   
 * @version V1.0     
 */
package Netty4.MQSource;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import javassist.expr.NewArray;

/**  
 * @ClassName: RemotingHelperr   
 * @Description: 通信层一些辅助的方法
 * @author: LUCKY  
 * @date:2016年4月21日 下午3:02:34     
 */
public class RemotingHelper {

    public static final String RemotingName = "RocketmqRemoting";

    public static String execptionSimpleDesc(final Throwable e) {
        StringBuffer sb = new StringBuffer();
        if (e != null) {
            sb.append(e.toString());
            StackTraceElement[] traceElements = e.getStackTrace();
            if (traceElements != null && traceElements.length > 0) {
                StackTraceElement element = traceElements[0];
                sb.append(",").append(element.toString());
            }
        }
        return sb.toString();
    }

    public static SocketAddress string2SocketAddress(final String address) {
        SocketAddress socketAddress = null;
        if (address != null && "" != address) {
            String[] s = address.split(":");
            socketAddress = new InetSocketAddress(s[0], Integer.parseInt(s[1]));
        }
        return socketAddress;
    }

    public static String parseChannelRemoteAddr(final Channel channel) {
        if (channel == null) {
            return "";
        }

        final SocketAddress remote = channel.remoteAddress();
        final String addr = remote != null ? remote.toString() : "";
        if (addr.length() > 0) {
            int index = addr.lastIndexOf("/");
            if (index >= 0) {
                return addr.substring(index + 1);
            }

            return addr;
        }

        return "";
    }

    public static String parseChannelRemoteName(final Channel channel) {
        if (channel == null) {
            return "";
        }

        final InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        if (socketAddress != null) {
            return socketAddress.getAddress().getHostName();
        }

        return "";
    }

    public static String parseSocketAddressAddr(final SocketAddress socketAddress) {
        if (socketAddress != null) {
            final String addr = socketAddress.toString();
            if (addr.length() > 0) {
                return addr.substring(1);
            }
        }

        return "";
    }

    public static String parseSocketAddressName(final SocketAddress socketAddress) {
        if (socketAddress == null) {
            return "";
        }

        final InetSocketAddress addrs = (InetSocketAddress) socketAddress;
        if (addrs != null) {
            return addrs.getAddress().getHostName();
        }

        return "";
    }

    public static void main(String[] args) {
        //        Channel channel=new NioSocketChannel();
        //        channel.connect(new InetSocketAddress(30));
        System.out.println(new InetSocketAddress(30).toString().substring(1));
        System.out.println();

    }
}
