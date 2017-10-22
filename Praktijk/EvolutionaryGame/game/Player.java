import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {

	public static final int VISION = 3;

	Vector f = new Vector(7, -20);
	Vector g = new Vector(0, 2);

	Vector a = new Vector(0, 0);
	Vector v = new Vector(0, 0);
	Vector p = new Vector(0, Tile.WIDTH * 2);

	int deadLine = 0;
	int hb = 40;
	int pos = 0;
	int t = 0;
	Score score = new Score();
	boolean jump = false;

	BufferedImage img;

	Player() {
		img = createImage(Color.getHSBColor((float) Math.random(), 1f, 1f),
				Color.getHSBColor((float) Math.random(), 1f, 1f));
	}

	public BufferedImage createImage(Color c1, Color c2) {
		BufferedImage img = new BufferedImage(hb, hb, BufferedImage.TYPE_INT_ARGB);
		Graphics g = img.createGraphics();
		g.setColor(c1);
		g.fillRect(0, 0, hb, hb);
		g.setColor(c2);
		g.fillRect(hb / 3 - 5, hb / 4, 10, 5);
		g.fillRect(hb / 3 * 2 - 5, hb / 4, 10, 5);

		return img;
	}

	public void move() {
		if (InputKeyboard.a) {
			v.x -= 2;
		}
		if (InputKeyboard.w) {
			v.y -= 2;
		}
		if (InputKeyboard.s) {
			v.y += 2;
		}
		if (InputKeyboard.d) {
			v.x += 2;
		}
	}

	public void jump() {
		a.add(f);

	}

	public boolean inAir(World world) {
		return (!world.getTileAt(p.x, p.y + 1).solid && !world.getTileAt(p.x + hb, p.y + 1).solid);
	}

	public void reset() {
		p.mult(0);
		v.mult(0);
		a.mult(0);
		score.reset();
		deadLine = 0;
		pos = 0;
		t = 0;
	}

	public void update(Game game) {
		deadLine += 3;
		if (p.x - deadLine > 80) {
			deadLine += 3;
		}
		if (p.x - deadLine > 160) {
			deadLine += 3;
		}
		score.addTime();
		score.xpos = (int) Math.floor(p.x / (double) Tile.WIDTH);

		input();

		a.mult(0.7);
		v.mult(0.7);

		if (jump && !inAir(game.world)) {
			jump();
		}

		if (inAir(game.world)) {
			a.add(g);
		}

		v.add(a);
		if (v.x < 3) {
			v.x = 3;
		}

		if (v.y > 0) {
			if (game.world.getTileAt(p.x, p.y + v.y).solid || game.world.getTileAt(p.x + hb, p.y + v.y).solid) {
				p.y = Math.max(game.world.getTileAt(p.x, p.y + v.y).y, game.world.getTileAt(p.x + hb, p.y + v.y).y);
				v.y = 0;
				a.y = 0;
			} else {
				p.y += v.y;
			}
		} else {
			p.y += v.y;
		}

		if (v.x > 0) {
			if (game.world.getTileAt(p.x + hb + 3, p.y - 5).solid) {
				p.x = (int) game.world.getTileAt(p.x + hb + v.x + 3, p.y).x - hb - 3;
				v.x = 0;
				a.x = 0;
			} else {
				p.x += v.x;
			}
		}

		if (p.x > Tile.WIDTH * World.WIDTH) {
			death(game);
		}

		if (deadLine - 500 > p.x) {
			death(game);
		}
		if (p.y > 800) {
			death(game);
		}

	}

	public void death(Game g) {
		g.running = false;
	}

	public void input() {
	}

}

class Score {
	int xpos;
	int time = 0;

	@Override
	public String toString() {
		return "Fitness: " + getFitness();
	}

	public double getFitness() {
		return Math.pow(time, 2) + 1;
	}

	public void reset() {
		time = 0;
		xpos = 0;
	}

	public void addTime() {
		time++;
	}
}