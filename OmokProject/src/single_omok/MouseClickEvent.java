package single_omok;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseClickEvent extends MouseAdapter {
	Map map;
	int x, y;
	MapSize mapSize;
	DrawBoard drawBoard;
	GUI gui;

	public MouseClickEvent(Map map, MapSize mapSize, DrawBoard drawBoard, GUI gui) {
		this.map = map;
		this.mapSize = mapSize;
		this.drawBoard = drawBoard;
		this.gui = gui;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		super.mousePressed(arg0);
		System.out.println("mouse");
		int x = (int) Math.round(arg0.getX() / (double) mapSize.getCell()) - 1;
		int y = (int) Math.round(arg0.getY() / (double) mapSize.getCell()) - 2;

		if (x<0 || x>19 || y<0 || y>19)
			return;
		if (map.getXY(y, x) == map.getBlack() || map.getXY(y, x) == map.getWhite())
			return;

		map.setMap(y, x);
		map.changeCheck();
		drawBoard.repaint();
		if (map.winCheck(x, y))
			if (map.getCheck() == true)
				gui.showPopUp("¹éµ¹ ½Â¸®!");
			else
				gui.showPopUp("Èæµ¹ ½Â¸®!");
	}
}