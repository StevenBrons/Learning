
public class Tile {

	static int WIDTH = 50;
	int x;
	int y;
	boolean solid = true;

	// een cell waaruit de wereld is opgebouwd
	public Tile(int posX, int posY) {
		x = posX;
		y = posY;
	}

}
