import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Tile {

	int cellWidth = 40;
	int x;
	int y;
	boolean solid = true;

	Random rand = new Random();
	int green = rand.nextInt(100) + 155;
	Color randomColor;
	public Color color;

	//een cell waaruit de wereld is opgebouwd
	public Tile(int posX, int posY) {
		x = posX;
		y = posY;
		randomColor = new Color(0, green, 0);
	}
	
	//teken de cell
	public void drawCell(Graphics g) {
		if (solid) {
			g.setColor(randomColor);
		} else {
			g.setColor(new Color(0, 0, 0, 0));
		}
		g.fillRect(x, y, cellWidth, cellWidth);
	}

}
