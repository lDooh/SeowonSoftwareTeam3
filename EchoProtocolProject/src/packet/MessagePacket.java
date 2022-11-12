package packet;

import constant.EchoCode;

import java.io.Serializable;

public class MessagePacket implements Serializable {
    private static final long serialVersionUID = 1234L;
    private EchoCode echoCode;
    private int repeat = 0;
    private String message = null;

    public MessagePacket(EchoCode echoCode, int n, String msg){
        this.echoCode = echoCode;
        this.repeat = n;
        this.message = msg;
    }

    public EchoCode getEchoCode() {
        return echoCode;
    }

    public void setEchoCode(EchoCode echoCode) {
        this.echoCode = echoCode;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int n) {
        this.repeat = n;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
