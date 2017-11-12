import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private Screen canvas = new Screen();

	static ArrayList<Cell> grid = new ArrayList<Cell>();
	ArrayList<Cell> stack = new ArrayList<Cell>();

	Cell current;

	public Frame() {
		setSize(640, 400);
		setTitle("Random Maze Generator: it's aMAZEing");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setExtendedState(MAXIMIZED_BOTH);
		setLayout(new BorderLayout());

		canvas.setBackground(Color.WHITE);
		add(canvas, BorderLayout.CENTER);
		add(new Menu(), BorderLayout.SOUTH);
		validate();
	}

	public void run() {
		grid.clear();
		stack.clear();

		for (int y = 0; y < Main.SIZE; y++) {
			for (int x = 0; x < Main.SIZE; x++) {
				Cell cell = new Cell(x, y);
				grid.add(cell);

			}
		}
		current = grid.get(0);

		canvas.run();
		repaint();
	}

	static void setStyle(JComponent c) {
		c.setBackground(Color.BLACK);
		c.setOpaque(true);
		c.setBorder(BorderFactory.createLineBorder(new Color(15, 15, 20), 3));
		c.setForeground(Color.WHITE);
		c.setFont(new Font(null, Font.BOLD, 20));
		if (c instanceof JLabel) {
			((JLabel) c).setHorizontalAlignment(JLabel.CENTER);
		}
		if (c instanceof JTextField) {
			((JTextField) c).setHorizontalAlignment(JLabel.CENTER);
		}
	}

	private class Screen extends JPanel {

		double mouseX = 0;
		double mouseY = 0;

		BufferedImage bf;

		private static final long serialVersionUID = 1L;

		public Screen() {
			addMouseMotionListener(new MouseMotionListener() {

				@Override
				public void mouseMoved(MouseEvent e) {
					mouseX = e.getX();
					mouseY = e.getY();
					repaint();
				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});

		}

		public void run() {
			bf = new BufferedImage(Main.SIZE * Main.CELL_WIDTH, Main.SIZE * Main.CELL_WIDTH,
					BufferedImage.TYPE_INT_ARGB);
			current.visited = true;
			Thread t = new Thread(new Runnable() {
				public void run() {
					int i = 0;
					drawMaze();
					try {
						while (true) {
							i++;
							if (i % 5 == 0) {
								if (Main.DELAY > 0) {
									drawMaze();
									repaint();
									Thread.sleep(Main.DELAY);
								}
							}
							Cell next = current.checkNeighbors();
							if (next != null) {
								next.visited = true;

								stack.add(current);
								current.depth = i;

								removeWalls(current, next);

								current = next;

							} else if (stack.size() > 0) {
								current = stack.get(stack.size() - 1);
								stack.remove(stack.size() - 1);
							} else if (stack.size() == 0) {
								break;
							}
						}
					} catch (Exception e) {
					}
					drawMaze();
				}
			});
			t.start();
		}

		public void drawMaze() {
			Graphics2D g = bf.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, bf.getWidth(), bf.getHeight());
			g.setStroke(new BasicStroke(Main.LINE_WIDTH, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
			for (int i = 0; i < grid.size(); i++) {
				grid.get(i).drawCell(g);
			}
			g.setStroke(new BasicStroke(Main.LINE_WIDTH * 2, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
			g.drawRect(0, 0, bf.getWidth(), bf.getHeight());
		}

		public void paintComponent(Graphics g) {
			if (bf == null) {
				return;
			}
			
			Graphics2D g2 = (Graphics2D) g;
			g2.clearRect(0, 0, getWidth(), getHeight());
			g2.translate(-mouseX / (double) getWidth() * bf.getWidth() + (getWidth() / 2),
					-mouseY / (double) getHeight() * bf.getHeight() + (getHeight() / 2));
			g2.drawImage(bf, 0, 0, null);

		}
	}

	public void removeWalls(Cell a, Cell b) {
		int x = a.x - b.x;
		int y = a.y - b.y;

		if (x == 1) {
			a.walls[3] = false;
			b.walls[1] = false;
		} else if (x == -1) {
			a.walls[1] = false;
			b.walls[3] = false;
		}

		if (y == 1) {
			a.walls[0] = false;
			b.walls[2] = false;
		} else if (y == -1) {
			a.walls[2] = false;
			b.walls[0] = false;
		}

	}

}
