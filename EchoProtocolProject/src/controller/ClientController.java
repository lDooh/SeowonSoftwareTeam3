package controller;

import seowonSocket.ClientThread;
import ui.ClientUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientController {
    private ClientUI clientUI;
    private ClientThread clientThread;

    public ClientController(ClientUI clientUI, ClientThread clientThread) {
        this.clientUI = clientUI;
        this.clientThread = clientThread;

        clientUI.setSendButtonListener(new SendButtonListener());
        clientUI.setExitButtonListener(new ExitButtonListener());
    }

    class SendButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 메시지를 입력하지 않았을 시 return
            String inputMessage = clientUI.getMessageText();
            if (inputMessage == null || inputMessage.isEmpty() || inputMessage.isBlank()) {
                JOptionPane.showMessageDialog(clientUI, "메시지를 입력해주세요.", "메시지 입력 오류",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                clientThread.setPacketMessage(inputMessage);
            }

            // 유효하지 않은 반복 횟수 입력 시 return
            int repeatNumber = clientUI.getRepeatNumber();
            if (repeatNumber < 0 || repeatNumber > 255) {
                JOptionPane.showMessageDialog(clientUI, "반복 횟수는 0~255입니다.", "반복 횟수 오류",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            } else {
                clientThread.setRepeatNumber(repeatNumber);
            }

            clientUI.emptyMessageTextField();
            clientUI.emptyRepeatNumberTextField();

            clientThread.setSendFlag(true);
        }
    }

    class ExitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            clientThread.setPacketMessage("");
            clientThread.setSendFlag(true);
        }
    }
}
