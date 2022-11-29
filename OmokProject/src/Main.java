import controller.OmokClientController;
import seowonSocket.OmokClientThread;
import ui.OmokBoardUI;

public class Main {
    public static void main(String[] args) {
        OmokBoardUI omokBoardUI = new OmokBoardUI();
        OmokClientThread omokClientThread = new OmokClientThread(omokBoardUI);
        OmokClientController omokClientController = new OmokClientController(
                omokBoardUI, omokClientThread);


    }
}