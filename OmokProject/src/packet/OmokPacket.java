package packet;

import constant.OmokPacketCode;

import java.io.Serializable;
import java.sql.Time;

public class OmokPacket implements Serializable {
    private static final long serialVersionUID = 1234L;
    private String player;
    private OmokPacketCode omokPacketCode;
    private int xPoint;
    private int yPoint;
    private Time time;

    public OmokPacket(OmokPacketCode omokPacketCode, int xPoint, int yPoint, String msg, Time time) {
        this.omokPacketCode = omokPacketCode;
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.player = msg;
        this.time = time;
    }

    public OmokPacketCode getOmokPacketCode() {
        return omokPacketCode;
    }

    public void setOmokPacketCode(OmokPacketCode omokPacketCode) {
        this.omokPacketCode = omokPacketCode;
    }

    public int getxPoint() {
        return xPoint;
    }

    public void setxPoint(int n) {
        this.xPoint = n;
    }

    public int getyPoint() {
        return xPoint;
    }

    public void getypPoint(int n) {
        this.yPoint = yPoint;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
