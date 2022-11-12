import controller.ClientController;
import seowonSocket.ClientThread;
import ui.ClientUI;

public class ClientMain {
    public static void main(String[] args) {
        ClientUI clientUI = new ClientUI();
        ClientThread clientThread = new ClientThread(clientUI);
        ClientController clientController = new ClientController(clientUI, clientThread);

        clientThread.start();
    }
}