import java.io.*;
import java.net.*;

import constant.EchoCode;
import constant.EchoServerState;
import packet.MessagePacket;

public class ChildServer extends Thread {
    private Socket socket = null;

    private InputStream is;
    private OutputStream os;

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private EchoServerState state;

    private boolean exitFlag = false;
    private String packetMessage;

    public ChildServer(Socket socket) {
        this.socket = socket;

        System.out.println(this.socket.getInetAddress() + "사용자 입장");

        try {
            is = this.socket.getInputStream();      //사용자로 부터 데이터를 입력받는 스트림
            os = this.socket.getOutputStream();     //사용자로 데이터 전송

            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        state = EchoServerState.SA; //사용자 접속 완료
    }

    @Override
    public void run(){
        MessagePacket messagePacket = null;
        int requestCount = 0;

        while (!exitFlag) {
            switch (state) {
                case SA: // 메세지 입력 대기 상태
                    try {
                        messagePacket = (MessagePacket) ois.readObject();
                        if (messagePacket.getEchoCode().equals(EchoCode.REQ_ECHO) && messagePacket != null) {
                            state = EchoServerState.SB;     // 메세지 전송 시작
                        }
                        else {
                            state = EchoServerState.SD;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    break;

                case SB:
                    messagePacket.setEchoCode(EchoCode.ACK_ECHO);
                    for (int i = 0; i < messagePacket.getRepeat(); i++) {
                        sendMessage(messagePacket);
                    }
                    requestCount++;     // 사용자가 메세지 n회 전송
                    state = EchoServerState.SC;     // 전송 완료 상태
                    break;

                case SC:    //  메세지 n회 반복전송 완료
                    state = EchoServerState.SA;
                    break;

                case SD:     // 사용자 메세지가 REQ_END 였을 때
                    messagePacket.setEchoCode(EchoCode.ACK_END);
                    messagePacket.setRepeat(requestCount);
                    messagePacket.setMessage(Integer.toString(requestCount) + "번 실행하셨습니다.");

                    sendMessage(messagePacket);
                    state = EchoServerState.SE;
                    break;

                case SE:    //  사용자 연결 종료
                    try {
                        is.close();
                        os.close();
                        ois.close();
                        oos.close();
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exitFlag = true;
                    break;
            }
        }
    }

    public void sendMessage(MessagePacket messagePacket) {
        try {
            oos.writeObject(messagePacket);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}