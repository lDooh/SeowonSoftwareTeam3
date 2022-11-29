package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OmokBoardUI extends JFrame {

    public OmokBoardUI() {
        super("");
        Container container = getContentPane();
        container.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setVisible(true);
    }

    public void setStoneButtonListener(ActionListener actionListener) {

    }

    public int getXPoint() {
        return 0;
    }

    public int getYPoint() {
        return 0;
    }

    public static void main(String[] args) {
        new OmokBoardUI();
    }
}