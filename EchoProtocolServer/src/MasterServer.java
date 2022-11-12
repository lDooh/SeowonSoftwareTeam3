import java.io.*;
import java.net.*;
import java.util.*;

public class MasterServer extends Thread {
    private ServerSocket serverSocket = null;   // 서버소켓 생성
    private final int PORT = 1234;
    private Socket childSocket = null;    // 소켓 생성

    public MasterServer() {

    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);  // 서버소켓 생성
        } catch (IOException e) {
            e.printStackTrace();
//            throw new RuntimeException(e);
        }

        while (true) {  //서버 시작
            try {
                childSocket = serverSocket.accept();  // 새로운 클라이언트 접속 대기

                ChildServer newChildServer = new ChildServer(childSocket);    // 새로운 자식 스레드에게 정보 전달
                newChildServer.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(0);
            }
        }
    }

    public static void main(String[] args) {
        MasterServer server = new MasterServer();
    }
}