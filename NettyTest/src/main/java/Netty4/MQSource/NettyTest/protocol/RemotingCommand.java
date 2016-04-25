/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Netty4.MQSource.NettyTest.protocol;

/**
 * @author LUCKY
 *
 */
public class RemotingCommand {
    public static String RemotingVersionKey = "remoting.version";
    private static volatile int ConfigVersion = -1;
    /**命令序列号  */
    private int opaque = 0;
    /**协议版本号  */
    private int version = 0;
    private RemotingCommandType type;
    /**
     * Body 部分
     */
    private transient byte[] body;
    
    public enum RemotingCommandType {
        REQUEST_COMMAND,
        RESPONSE_COMMAND;
    }


    public static RemotingCommand createRequestCommand() {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.REQUEST_COMMAND);
        return cmd;
    }

    /**
     * 只有通信层内部会调用，业务不会调用
     */
    public static RemotingCommand createResponseCommand(byte[] body) {
        RemotingCommand cmd = new RemotingCommand();
        setCmdVersion(cmd);
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        cmd.setBody(body);
        return cmd;
    }
    public static RemotingCommand createResponseCommand(int code, String remark) {
        RemotingCommand cmd = new RemotingCommand();
        String str = "error["+code+"] "+remark;
        cmd.setBody(str.getBytes());
        cmd.setType(RemotingCommandType.RESPONSE_COMMAND);
        setCmdVersion(cmd);
        
        return cmd;
    }

    private static void setCmdVersion(RemotingCommand cmd) {
        if (ConfigVersion >= 0) {
            cmd.setVersion(ConfigVersion);
        }
        else {
            String v = System.getProperty(RemotingVersionKey);
            if (v != null) {
                int value = Integer.parseInt(v);
                cmd.setVersion(value);
                ConfigVersion = value;
            }
        }
    }

    public byte[] getBody() {
        return body;
    }


    public void setBody(byte[] body) {
        this.body = body;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
    public RemotingCommandType getType() {
        return type;
    }
    public void setType(RemotingCommandType type) {
        this.type = type;
    }

    public int getOpaque() {
        return opaque;
    }

    public void setOpaque(int opaque) {
        this.opaque = opaque;
    }

    @Override
    public String toString() {
        return "RemotingCommand [version=" + version  + ", opaque=" + opaque + "]";
    }

   

}
