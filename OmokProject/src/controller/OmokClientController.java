package controller;

import seowonSocket.OmokClientThread;
import ui.OmokBoardUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OmokClientController {
    private OmokBoardUI omokBoardUI;
    private OmokClientThread omokClientThread;

    public OmokClientController(OmokBoardUI omokBoardUI, OmokClientThread omokClientThread) {
        this.omokBoardUI = omokBoardUI;
        this.omokClientThread = omokClientThread;

        omokBoardUI.setStoneButtonListener(new StoneButtonListener());
    }

    class StoneButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            omokClientThread.setPoint(omokBoardUI.getYPoint(),
                    omokBoardUI.getXPoint());

            omokClientThread.setPutFlag(true);
        }
    }
}
