import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	private MyCanvas canvas = new MyCanvas();
	static int cols;
	static int rows;
	static int CellWidth = 4;
	static ArrayList<Cell> grid = new ArrayList<Cell>();
	ArrayList<Cell> stack = new ArrayList<Cell>();
	
	Cell current;

	public Frame() {
		setSize(1040, 1060);
		setTitle("Random Maze Generator: it's aMAZEing");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setLayout(new BorderLayout());

		cols = (int) Math.floor((getWidth() - 40) / CellWidth);
		rows = (int) Math.floor((getHeight() - 40) / CellWidth);

		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				Cell cell = new Cell(x, y);
				grid.add(cell);

			}
		}

		current = grid.get(0);
		canvas.setBackground(Color.WHITE);
		add(canvas, BorderLayout.CENTER);
		canvas.run();
	}

	private class MyCanvas extends Canvas {

		private static final long serialVersionUID = 1L;

		public void run() {
			current.visited = true;
			Thread t = new Thread(new Runnable() {
				public void run() {
					int i = 0;
					try {
						while (true) {
							i++;
							if (i % 5000 == 0){
								Thread.sleep(500);								
							}
							Cell next = current.checkNeighbors();
							if (next != null) {
								next.visited = true;
								
								stack.add(current);
								System.out.println(i);
								current.depth = i;

								removeWalls(current, next);

								current = next;
								repaint();
							} else if (stack.size() > 0) {
								current = stack.get(stack.size() - 1);
								stack.remove(stack.size() - 1);
							} else if (stack.size() == 0) {
								break;
							}
						}
					} catch (Exception e) {
						System.out.println("Error in Thread");
					}
				}
			});
			t.start();
		}
		
		@Override
		public void paint(Graphics g) {
			for (int i = 0; i < grid.size(); i++) {
				grid.get(i).drawCell(g);
			}
			
		}
	}

	public void removeWalls(Cell a, Cell b) {
		int x = a.x - b.x;
		int y = a.y - b.y;

//		System.out.println("x = " + x + " y = " + y);

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
