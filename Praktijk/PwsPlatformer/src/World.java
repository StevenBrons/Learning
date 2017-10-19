import java.awt.Color;
import java.util.Random;

public class World {

	Tile[][] world = new Tile[128][16];
	int cellWidth = 40;

	Random r = new Random();

	public World(int type) {
		// wereld genereren
		for (int x = 0; x < world.length; x++) {
			for (int y = 0; y < world[x].length; y++) {
				Tile t = new Tile(x * cellWidth, y * cellWidth);
				world[x][y] = t;
			}
		}

		switch (5) {
		case 0:
			break;
		case 1:
			makeHobbels();
			break;
		case 2:
			makeKuiltjes();
			break;
		case 3:
			makePits();
			break;
		case 4:
			makeHobbels();
			break;
		case 5:
			makeKuiltjes();
			makePits();
			break;
		default:
			break;
		}

	}

	public void makeKuiltjes() {
		for (int i = 0; i < 25; i++) {
			if (r.nextInt(3) <= 1) {
				int a = r.nextInt(108) + 10;
				world[a][0].solid = false;
				world[a][1].solid = false;
				world[a + 1][0].solid = false;
				world[a - 1][0].solid = false;

			} else {
				int a = r.nextInt(108) + 10;
				world[a][1].solid = true;
			}
		}

	}

	public void makeHobbels() {

		for (int i = 0; i < world.length; i++) {
			world[i][0].solid = false;
			world[i][1].solid = false;
		}

		for (int i = 0; i < 25; i++) {
			if (r.nextInt(3) > 1) {
				int a = r.nextInt(108) + 10;
				world[a][0].solid = true;
				world[a][1].solid = true;
				world[a + 1][1].solid = true;
				world[a - 1][1].solid = true;

			} else {
				int a = r.nextInt(108) + 10;
				world[a][1].solid = true;
			}
		}

	}

	public void makePits() {
		for (int i = 0; i < 25; i++) {
			int a = r.nextInt(108) + 10;
			if (world[a - 1][0].solid && world[a + 1][0].solid || world[a - 1][1].solid && world[a + 1][1].solid) {
				for (int j = 0; j < world[a].length; j++) {
					world[a][j].solid = false;
				}
			} else {
				i--;
			}

		}
	}

	public boolean detectTile(double x, double y) {
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world[i].length; j++) {
				if (x > i * cellWidth && x < (i + 1) * cellWidth) {
					if (y > j * cellWidth) {
						if (world[i][j].solid) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public double groundHeight(double posX, double posY) {

		for (int i = 0; i < world.length; i++) {
			if (posX > i * cellWidth && posX < (i + 1) * cellWidth) {
				for (int j = 0; j < world[i].length; j++) {
					if (world[i][j].solid) {
						return j * cellWidth;
					}
				}
			}
		}

		return 100;
	}

}
