import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Screen extends Canvas {

	private static final long serialVersionUID = 1L;

	BufferedImage screen;
	Graphics2D g;
	Graphics2D g2;
	
	Color background = new Color(36, 150, 232);
	
	double currentTime = System.currentTimeMillis();
	int secondsRunning = 60;
	
	// een canvas
	public Screen() {
//		setBackground(new Color(36, 150, 232));
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
		
		//clear het hele scherm
		g.clearRect(0, 0, getWidth(), getHeight());
		
		//maak de achtergrond (het ehle scherm) blauw
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		//zet de speler in een vatse positie
		g.translate(-game.player.posX, 0);
		g.translate(getWidth() / 2, getHeight() / 2);
		
		//teken de wereld en de speler
		drawWorld(g, game.world);
		game.player.drawPlayer(g);
		
		g.setTransform(new AffineTransform());
	}
	
	public void drawToScreen(){
		g2.drawImage(screen, 0, 0, null);
	}

	public void drawWorld(Graphics g, World world) {
		for (int x = 0; x < world.world.length; x++) {
			for (int y = 0; y < world.world[x].length; y++) {
				world.world[x][y].drawCell(g);
			}
		}
		
	}
	
	public void countTime(){
		double temp = System.currentTimeMillis();
		if (temp - currentTime > 1000) {
			secondsRunning--;
			currentTime = temp;
		}
	}
	
}
