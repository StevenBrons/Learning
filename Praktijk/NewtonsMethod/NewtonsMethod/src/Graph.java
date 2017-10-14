import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Graph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double xmin = -5;
	double xmax = 5;
	double ymin = -20;
	double ymax = 20;

	static ArrayList<Double> pos = new ArrayList<>();
	static double best = 100000;

	double xstep = 0.001;
	double ystep = 0.001;

	Graph() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				run();
			}
		});

	}

	@Override
	public void paint(Graphics g2) {
		Graphics2D g = (Graphics2D) g2;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.WHITE);
		g.translate(getWidth() / 2, getHeight() / 2);
		g.setFont(new Font(null, Font.PLAIN, 20));
		g.drawString("O", 5, 20);
		g.drawString("x", getWidth() / 2 - 30, 20);
		g.drawString("y", 20, -getHeight() / 2 + 50);
		g.drawLine(-5000, 0, 5000, 0);
		g.drawLine(0, -5000, 0, 5000);

		drawLines(g);
		drawFunction(g);
		drawPoints(g);

	}

	public static void run() {
		if (pos.size() == 0) {
			return;
		}
		double x = pos.get(pos.size() - 1);
		double y = Settings.f.getValue(x);

		double a = Settings.d.getValue(x);
		double b = y - (a * x);

		pos.add(-b / a);

		if (best > Math.abs(y)) {
			best = Math.abs(y);
			Main.f.menu.best.setText("Best: " + new DecimalFormat("#.####").format(y) + "");
		}
		Main.f.menu.cur.setText("Curent: " + new DecimalFormat("#.####").format(y) + "");
		Main.f.repaint();
	}

	void drawFunction(Graphics2D g) {
		Path2D p = new Path2D.Double();
		int w = getWidth() / 2;
		p.moveTo(normX(xmin), normY(Settings.f.getValue(-w)));
		double stepX = (xmax - xmin) / 100.0;
		for (double x = xmin; x < xmax; x += stepX) {
			p.lineTo(normX(x), normY(Settings.f.getValue(x)));
		}
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(5));
		g.draw(p);
	}

	void drawLines(Graphics2D g) {
		g.setStroke(new BasicStroke(1));
		for (int i = 0; i < pos.size(); i++) {
			g.setColor(Color.getHSBColor(((i * 1f) / (float) pos.size()), 1, 1));

			if (pos.size() - 1 == i) {
				g.setStroke(new BasicStroke(5));
			}

			double x = pos.get(i);
			double y = Settings.f.getValue(x);

			double a = Settings.d.getValue(x);
			double b = y - (a * x);
			g.drawLine(normX(-10000), normY(a * -10000 + b), normX(10000), normY(a * 10000 + b));
		}
	}

	void drawPoints(Graphics g) {
		for (int i = 0; i < pos.size(); i++) {
			g.setColor(Color.getHSBColor(((i * 1) / (float) pos.size()), 1, 1));

			if (pos.size() - 1 == i) {
				g.fillOval(normX(pos.get(i)) - 10, normY(Settings.f.getValue(pos.get(i))) - 10, 20, 20);
			} else {
				g.fillOval(normX(pos.get(i)) - 5, normY(Settings.f.getValue(pos.get(i))) - 5, 10, 10);
			}
		}
	}

	public int normX(double x) {
		return (int) (x * getWidth() / -(xmin - xmax));
	}

	public int normY(double y) {
		return (int) (y * getWidth() / (ymin - ymax));
	}

}
