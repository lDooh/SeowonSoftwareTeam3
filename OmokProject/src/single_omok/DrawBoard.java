package single_omok;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DrawBoard extends JPanel {
	private MapSize mapSize;
	private Map map;
	private final int STONE_SIZE = 28; // ?? ??????

	public DrawBoard(MapSize m, Map map) {
		setBackground(new Color(206, 167, 60));
		mapSize = m;
		setLayout(null);
		this.map = map;
	}

	@Override
	public void paintComponent(Graphics arg) {
		super.paintComponent(arg);
		arg.setColor(Color.BLACK);
		board(arg); 
		drawStone(arg);
	}

	public void board(Graphics arg0) {
		for (int i = 1; i <= mapSize.getSize(); i++) {
			// ???? ??
			arg0.drawLine(mapSize.getCell(), i * mapSize.getCell(), mapSize.getCell() * mapSize.getSize(), i * mapSize.getCell());
			// ??????
			arg0.drawLine(i * mapSize.getCell(), mapSize.getCell(), i * mapSize.getCell(), mapSize.getCell() * mapSize.getSize()); 									// 600
		}
	}

	public void drawStone(Graphics arg0) {
		for (int y = 0; y < mapSize.getSize(); y++) {
			for (int x = 0; x < mapSize.getSize(); x++) {
				if (map.getXY(y, x) == map.getBlack())
					drawBlack(arg0, x, y);
				else if (map.getXY(y, x) == map.getWhite())
					drawWhite(arg0, x, y);
			}
		}
	}

	public void drawBlack(Graphics arg0, int x, int y) {
		arg0.setColor(Color.BLACK);
		arg0.fillOval((x + 1) * mapSize.getCell() - 15, (y) * mapSize.getCell() + 15, STONE_SIZE, STONE_SIZE);
	}

	public void drawWhite(Graphics arg0, int x, int y) {
		arg0.setColor(Color.WHITE);
		arg0.fillOval(x * mapSize.getCell() + 15, y * mapSize.getCell() + 15, STONE_SIZE, STONE_SIZE);
	}
}