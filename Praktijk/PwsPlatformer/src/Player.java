import java.awt.Color;
import java.awt.Graphics;

public class Player {

	double posX;
	double posY;
	World world;

	double g = 0.5;
	double v = 0;
	double a = 0;
	double f = 30;

	boolean jump = false;

	int w = 40;
	int hitbox = 20;

	Player(double i, double j, World w) {
		posX = i;
		posY = j;
		world = w;
	}

	public void drawPlayer(Graphics g) {
		// teken de player
		g.setColor(Color.BLACK);
		g.fillOval((int) Math.floor(posX - 0.5 * w), (int) Math.floor(posY - 0.5 * w), w, w);
		// teken de 'hitbox'
		g.setColor(Color.RED);
		g.fillOval((int) Math.floor(posX - 0.5 * hitbox), (int) Math.floor(posY - 0.5 * hitbox), hitbox, hitbox);

	}

	public void move() {
		// Als je geen autowalk wil:
//		 if (InputKeyboard.d) {
//		 if (!tileRight((int) Math.floor(posX), (int) Math.floor(posY))) {
//		 posX++;
//		 }
//		 }
//
//		 if (InputKeyboard.a) {
//		 if (!tileLeft((int) Math.floor(posX), (int) Math.floor(posY))) {
//		 posX--;
//		 }
//		 }

		if (!tileRight((int) Math.floor(posX), (int) Math.floor(posY))) {
			posX++;
		}
	}

	public void jump() {

		a += f;

	}

	public boolean inAir() {

		return !inTile(posX, posY);
	}

	// kijkt of een positie (i, j) ín een solid tile zit
	public boolean inTile(double i, double j) {

		if (world.detectTile(i, j)) {
			return true;
		}

		return false;
	}

	public boolean tileRight(int i, int j) {
		// System.out.println((i) + "\t" + j + "\t" + world.detectTile(i + 2, j));
		return world.detectTile(i + 0.5 * hitbox, j);
	}

	public boolean tileLeft(int i, int j) {
		// System.out.println((i - 2) + "\t" + j + "\t" + world.detectTile(i - 2, j));
		return world.detectTile(i - 0.5 * hitbox, j);
	}

	public void update() {

		if (posY > 41) {
			System.out.printf("%s\t%s\t\n", posX, posY);
		}

		// tijdelijke versie van respawnen
		if (posY > 400) {
			posX = 10;
			posY = 0;
		}

		input();

		a *= 0.7;
		v *= 0.3;

		if (jump && !inAir()) {
			jump();
		}

		if (inAir()) {
			a -= g;
		}

		// zorgen dat de speler niet de grond in zakt
		if (inTile((int) Math.floor(posX), (int) Math.floor(posY))) {
			posY = world.groundHeight(posX, posY);
		}

		// zorgen dat de speler niet de grond in zakt 2
		if (tileRight((int) posX, (int) posY) && tileLeft((int) posX, (int) posY)) {
			posY = 0;
		}

		v += a;

		// posX++;
		move();
		posY -= v;
		// System.out.printf("%s\t%s\t%s\t\n", posY, v , a);
		// System.out.printf("%s\t%s\t\n", posX, posY);
	}

	public void input() {

	}

}
