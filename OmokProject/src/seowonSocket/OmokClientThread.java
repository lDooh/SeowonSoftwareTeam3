package seowonSocket;

import constant.OmokClientState;
import constant.OmokPacketCode;
import packet.OmokPacket;
import ui.OmokBoardUI;

import java.io.*;
import java.net.Socket;

public class OmokClientThread extends Thread {
    private OmokClientState state;
    private Socket socket = null;
    private String serverIp = "127.0.0.1";
    private int serverPort = 1234;
    private InputStream is;
    private OutputStream os;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    private int repeatNumber = 0;
    private boolean exitFlag = false;
    private boolean putFlag = false;

    private OmokPacket omokPacket;
    private int x, y;
    private String playerName = null;

    private OmokBoardUI omokBoardUI;

    public OmokClientThread(OmokBoardUI omokBoardUI) {
        this.omokBoardUI = omokBoardUI;

        try { // 소켓 및 스트림 열기
            socket = new Socket(serverIp, serverPort);
            
            // TODO: INIT, ACK를 받아서 흑돌인지 백돌인지 playerName을 받음

            os = socket.getOutputStream();
            is = socket.getInputStream();
            oos = new ObjectOutputStream(os);
            ois = new ObjectInputStream(is);
            state = OmokClientState.S1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printState() { // 디버깅용 상태 출력
        System.out.println("Client State: " + state.toString());
    }

    @Override
    public void run() {
        omokPacket = null;
        playerName = "";        // 플레이어 이름 설정

        System.out.println("매칭 중 . . . . ");

        while (!exitFlag) {
            switch (state) {
                case S1: // 착수 전
                    printState();

                    // putFlag가 true가 되기 전까지 대기
                    while (!putFlag) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        printState();
                    }

                    putFlag = false;       // 다시 false로 바꿔놓음
                    
                    state = OmokClientState.S2;
                    break;

                case S2: // 서버에 착수 정보 전송 전
                    printState();
                    
                    omokPacket = new OmokPacket(OmokPacketCode.REQ_PUTSTONE, x, y, playerName, null);
                    state = OmokClientState.S3;
                    sendMessage(omokPacket);
                    break;

                case S3: // 서버에 착수 정보 전송 후, ACK를 받기 전, 받은 후엔 UI 적용
                    printState();

                    try {
                        omokPacket = (OmokPacket) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    // UI 클래스에 좌표 정보 전달, UI 적용하는 메소드
//                    omokBoardUI.displayPutStone();
                    
                    state = OmokClientState.S4;
                    break;

                case S4: // 게임 결과 ACK를 받기 전
                    printState();
                    
                    try {
                        // 게임이 끝났는지에 대한 ACK를 받음
                        omokPacket = (OmokPacket) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    
                    state = OmokClientState.S5;
                    break;

                case S5:    // 게임 결과 ACK를 받은 후
                    printState();

                    // 게임이 끝났다면 상태 S8로
                    if (isGameEnd(omokPacket)) {
                        state = OmokClientState.S8;
                    }
                    else {
                        try {
                            // 상대방의 착수 정보를 받아옴
                            omokPacket = (OmokPacket) ois.readObject();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        
                        // 상대방의 착수를 UI에 적용
//                        omokBoardUI.displayPutStone();
                        state = OmokClientState.S6;
                    }
                    break;

                case S6: // 상대방 착수 ACK를 받은 후
                    printState();

                    try {
                        // 게임 결과에 대한 ACK를 받음
                        omokPacket = (OmokPacket) ois.readObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    state = OmokClientState.S7;
                    break;
                    
                case S7:    // 게임 결과 ACK를 받은 후
                    if (isGameEnd(omokPacket)) {
                        state = OmokClientState.S8;
                    }
                    else {
                        state = OmokClientState.S1;
                    }
                    break;

                case S8: // 게임 종료
                    printState();
                    try {
                        oos.close();
                        ois.close();
                        is.close();
                        os.close();
                        socket.close();
                        System.out.println("연결을 종료합니다.");
//                        System.out.println(omokPacket.getPlayer());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    exitFlag = true;
                    omokBoardUI.dispose();
                    break;
            }
        }
    }

    public void sendMessage(OmokPacket packet){
        try{
            oos.writeObject(packet);
            oos.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private boolean isGameEnd(OmokPacket packet) {
        // 전달받은 좌표가 둘 다 -1이면 게임 끝
        if (packet.getxPoint() == -1 && packet.getyPoint() == -1) {
            return true;
        }
        else {
            return false;
        }
    }

    public void setPutFlag(boolean putFlag) {
        this.putFlag = putFlag;
    }

    public void setPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
