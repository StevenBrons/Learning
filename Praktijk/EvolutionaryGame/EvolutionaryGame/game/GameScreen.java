import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GameScreen extends Canvas {

	private static final long serialVersionUID = 1L;

	BufferedImage screen;
	Graphics2D g;
	Graphics2D g2;

	Game game;
	int scale;

	// een canvas
	public GameScreen() {

		addKeyListener(new InputKeyboard());

		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				g = screen.createGraphics();
				g2 = (Graphics2D) getGraphics();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						if (game != null) {
							drawAll(game);
							drawToScreen();
						}
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		t.start();

	}

	public void init() {
		screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
		g = screen.createGraphics();
		g2 = (Graphics2D) getGraphics();
	}

	public void drawAll(Game game) {
		if (g == null) {
			return;
		}

		// clear het hele scherm
		g.clearRect(0, 0, getWidth(), getHeight());

		// maak de achtergrond (het hele scherm) blauw
		GradientPaint gp = new GradientPaint(0, 0, new Color(168, 233, 255), 0, getHeight(),
				new Color(15, 128, 219));
		g.setPaint(gp);
		g.fillRect(0, 0, getWidth(), getHeight());

		drawScores(g, game.player);

		// zet de speler in een vatse positie
		g.translate(-game.player.deadLine, -250);
		g.translate(getWidth() / 2 - 200, getHeight() / 2);
		
		// teken de wereld en de speler
		drawWorld(g, game.world);
		drawPlayer(g, game.player);
		
		g.setColor(Color.RED);
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND, 1f, new float[] { 20 }, 1));
		g.drawLine(Math.round(game.player.deadLine - 500), -1000, Math.round(game.player.deadLine - 500), 1000);
		g.setTransform(new AffineTransform());
	}

	private void drawScores(Graphics2D g, Player p) {
		g.setColor(Color.BLACK);
		g.setFont(new Font(null, Font.PLAIN, 20));
		g.drawString("Time: " + p.score.time, getWidth() - 300, 30);
		g.drawString("Fitness: " + p.score.getFitness(), getWidth() - 300, 50);
		g.drawString("Pos: (" + p.score.xpos + "/" + World.WIDTH + ")", getWidth() - 300, 70);

	}

	private void drawPlayer(Graphics2D g, Player p) {
		// teken de player
		g.drawImage(p.img, (int) ((int) p.p.x), (int) p.p.y - p.hb, null);
	}

	public void drawToScreen() {
		if (g2 != null) {
			g2.drawImage(screen, 0, 0, null);
		}
	}

	public void drawWorld(Graphics g, World world) {
		for (int x = world.world.length - 1; x >= 0; x--) {
			for (int y = 0; y < world.world[x].length; y++) {
				drawTile(world.world[x][y], x, y);
			}
		}

	}

	public void drawTile(Tile t, int x, int y) {
		if (t.solid) {
			GradientPaint gp = new GradientPaint(0, 0, Color.GREEN, 0, (float) (getHeight()),
					new Color(139, 69, 19).darker(), true);
			g.setPaint(gp);
			g.fillRect(x * Tile.WIDTH, y * Tile.WIDTH, Tile.WIDTH, Tile.WIDTH);
			g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
		}

	}

}
