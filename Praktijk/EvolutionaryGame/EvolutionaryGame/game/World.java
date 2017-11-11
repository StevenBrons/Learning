import java.util.Random;

public class World {

	public static final int WIDTH = 128;
	public static final int HEIGHT = 16;

	Tile[][] world = new Tile[WIDTH][HEIGHT];
	int cellWidth = 50;

	Random r = new Random();

	public World(int type) {
		// wereld genereren
		for (int x = 0; x < world.length; x++) {
			for (int y = 0; y < world[x].length; y++) {
				Tile t = new Tile(x * cellWidth, y * cellWidth);
				if (y < 2) {
					t.solid = false;
				}
				world[x][y] = t;
			}
		}

		switch (type) {
		case 0:
			test();
			break;
		case 1:
			makeTerrain();
			break;
		case 2:
			makePits();
			break;
		case 3:
			makeTerrain();
			makePits();
			break;
		default:
			break;
		}

	}

	private void test() {
		int a = 2;
		for (int j = 0; j < world[a].length; j++) {
			world[a][j].solid = false;
		}
		a = 3;
		for (int j = 0; j < world[a].length; j++) {
			world[a][j].solid = false;
		}
	}

	public void makeTerrain() {
		double a = 3;

		for (int i = 0; i < WIDTH; i++) {
			clearTill(i, (int) Math.ceil(a));
			double d = r.nextGaussian();
			if (d > 1.5 || d < -1.5) {
				d = 1;
			}
			a += d;
			if (a < 0) {
				a = 1;
			}
			if (a > HEIGHT - 2) {
				a = HEIGHT - 3;
			}
			System.out.println(a);
		}
	}

	public void clearTill(int x, int ymax) {
		for (int j = 0; j < ymax; j++) {
			world[x][j].solid = false;
		}
	}

	public void makePits() {
		int a = 3;
		while (a < WIDTH) {
			clearTill(a, HEIGHT);
			a++;
			if (r.nextInt(2) == 0 && a < WIDTH) {
				clearTill(a, HEIGHT);
			}
			a += r.nextInt(3) + 2;
		}
	}

	public Tile getTileAt(double x, double y) {
		if (x < 0 || y < 0 || x > (cellWidth * WIDTH) || y > (cellWidth * HEIGHT)) {
			Tile air = new Tile(0, 0);
			air.solid = false;
			return air;
		}
		return world[(int) Math.floor(x / cellWidth)][(int) Math.floor(y / cellWidth)];
	}

	public Tile[][] getRange(int x, int y, int width, int height) {
		Tile[][] dest = new Tile[height][width];
		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				dest[xx][yy] = getTileAt((xx + x) * Tile.WIDTH, (yy + y) * Tile.WIDTH);
			}
		}
		return dest;
	}

}
