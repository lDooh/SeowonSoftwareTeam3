package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class ClientUI extends JFrame {
    private JScrollPane jScrollPane;
    private Vector<String> receiveMessageVector;
    private JList messageList;
    private JLabel messageLabel, repeatLabel;
    private JTextField messageTextField;
    JFormattedTextField repeatTextField;
    private JButton sendButton, exitButton;

    public ClientUI() {
        super("");
        Container container = getContentPane();
        container.setLayout(null);

        receiveMessageVector = new Vector<>();
        messageList = new JList(receiveMessageVector);

        jScrollPane = new JScrollPane(messageList,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setBounds(30, 30, 500, 500);
        jScrollPane.setBorder(new LineBorder(Color.BLACK));
        container.add(jScrollPane);

        messageLabel = new JLabel("메시지 입력");
        messageLabel.setBounds(550, 30, 300, 40);
        container.add(messageLabel);

        messageTextField = new JTextField();
        messageTextField.setBounds(550, 80, 300, 40);
        container.add(messageTextField);

        repeatLabel = new JLabel("반복 횟수 입력");
        repeatLabel.setBounds(550, 180, 300, 40);
        container.add(repeatLabel);
        // 반복 횟수를 입력하는 textField는 숫자만 입력받음
        repeatTextField = new JFormattedTextField(new NumberFormatter());
        repeatTextField.setBounds(550, 230, 300, 40);
        repeatTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // 최대 3글자 입력
                if (((JFormattedTextField)e.getSource()).getText().length() > 2) {
                    e.consume();
                }
            }
        });
        container.add(repeatTextField);

        sendButton = new JButton("전송하기");
        sendButton.setBounds(550, 400, 150, 40);
        container.add(sendButton);

        exitButton = new JButton("종료하기");
        exitButton.setBounds(720, 400, 150, 40);
        container.add(exitButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setVisible(true);
    }

    public void setSendButtonListener(ActionListener listener) {
        // REQ_PACKET 보냄
        sendButton.addActionListener(listener);
    }

    public void setExitButtonListener(ActionListener listener) {
        // REQ_END 보내고 ACK_END 받으면 종료
        exitButton.addActionListener(listener);
    }

    public String getMessageText() {
        return messageTextField.getText();
    }

    public void emptyMessageTextField() {
        messageTextField.setText("");
    }

    public int getRepeatNumber() {
        return Integer.parseInt(repeatTextField.getText());
    }

    public void emptyRepeatNumberTextField() {
        repeatTextField.setText("");
    }

    public void addReceiveMessage(String receiveMessage) {
        receiveMessageVector.add(receiveMessage);
    }
    
    public void setReceiveMessage() {
        messageList.setListData(receiveMessageVector);
    }

    public static void main(String[] args) {
        new ClientUI();
    }
}