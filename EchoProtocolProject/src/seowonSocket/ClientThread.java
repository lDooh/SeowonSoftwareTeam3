package seowonSocket;

import constant.EchoClientState;
import constant.EchoCode;
import packet.MessagePacket;
import ui.ClientUI;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private EchoClientState state;
    private Socket socket = null;
    // TODO: IP 주소 입력
    private String serverIp = "";
    private int serverPort = 1234;
    private InputStream is;
    private OutputStream os;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private int repeatNumber = 0;
    private boolean exitFlag = false;
    private boolean sendFlag = false;

    private MessagePacket messagePacket;
    private String packetMessage = null;

    private ClientUI clientUI;

    public ClientThread(ClientUI clientUI) {
        this.clientUI = clientUI;

        try { // 소켓 및 스트림 열기
            socket = new Socket(serverIp, serverPort);

            os = socket.getOutputStream();
            is = socket.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
            state = EchoClientState.S1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printState() { // 디버깅용 상태 출력
        System.out.println("Client State: " + state.toString());
    }

    @Override
    public void run() {
        messagePacket = null;
        packetMessage = "";

        System.out.println("서버 접속 시도 중 . . . . ");

        while (!exitFlag) {
            switch (state) {
                case S1: // 메세지 입력 받기 전
                    printState();

                    // sendFlag가 true가 되기 전까지 대기
                    while (!sendFlag) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        printState();
                    }

                    sendFlag = false;       // 다시 false로 바꿔놓음
                    
                    state = EchoClientState.S2;
                    break;

                case S2: // 메세지를 입력받은 후
                    printState();

                    if (packetMessage.equals("")) {
                        state = EchoClientState.S5;     // end = True
                    } else {
                        messagePacket = new MessagePacket(EchoCode.REQ_ECHO, repeatNumber, packetMessage);
                        state = EchoClientState.S3;
                        sendMessage(messagePacket);
                    }
                    break;

                case S3: // 메세지를 서버로 송신한 후
                    printState();

                    try {
                        messagePacket = (MessagePacket) ois.readObject();
                        clientUI.addReceiveMessage(messagePacket.getMessage());

                        for (int i = 0; i < messagePacket.getRepeat() - 1; i++) {
                            messagePacket = (MessagePacket) ois.readObject();
                            clientUI.addReceiveMessage(messagePacket.getMessage());
                        }
                        
                        clientUI.setReceiveMessage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    
                    state = EchoClientState.S4;
                    break;

                case S4: // 메세지를 수신받은 후
                    printState();
                    state = EchoClientState.S1;
                    System.out.println("수신완료");
                    break;

                case S5: // END = TRUE, 서버로 REQ_END 송신하기 전
                    printState();
                    packetMessage = "";
                    repeatNumber = 0;
                    messagePacket = new MessagePacket(EchoCode.REQ_END, repeatNumber, packetMessage);
                    sendMessage(messagePacket);
                    state = EchoClientState.S6;
                    break;

                case S6: // 서버로부터 ACK_END 수신
                    printState();
                    try {
                        messagePacket = (MessagePacket) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    state = EchoClientState.S7;
                    break;

                case S7: // ACK_END 수신 이후
                    printState();
                    try {
                        oos.close();
                        ois.close();
                        is.close();
                        os.close();
                        socket.close();
                        System.out.println("연결을 종료합니다.");
                        System.out.println(messagePacket.getMessage());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exitFlag = true;
                    clientUI.dispose();
                    break;
            }
        }
    }
    public void sendMessage(MessagePacket packet){
        try{
            oos.writeObject(packet);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setPacketMessage(String packetMessage){
        this.packetMessage = packetMessage;
    }

    public void setRepeatNumber(int repeatNumber) {
        this.repeatNumber = repeatNumber;
    }
    
    public void setSendFlag(boolean sendFlag) {
        this.sendFlag = sendFlag;
        System.out.println("flag 변경: " + this.sendFlag);
    }
}
