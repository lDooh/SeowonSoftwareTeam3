package single_omok;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class 	GUI extends JFrame {
	Container c;
	MapSize mapSize = new MapSize();
	
	public GUI(String title) {
		super(title);
		c = getContentPane();
		setBounds(200, 20, 650, 700);
		c.setLayout(null);
		Map map = new Map(mapSize);
		DrawBoard board = new DrawBoard(mapSize, map);
		setContentPane(board);
		addMouseListener(new MouseClickEvent(map, mapSize, board, this));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showPopUp(String message) {
		JOptionPane.showMessageDialog(this, message, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}
	
}