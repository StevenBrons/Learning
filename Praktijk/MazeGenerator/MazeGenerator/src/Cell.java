import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Cell {

	int w = Frame.CellWidth;
	Boolean[] walls = new Boolean[4];
	int x; 
	int y;
	boolean visited;
	int depth;
	
	public Cell(int i, int j) {
		x = i;
		y = j;
		walls[0] = true;
		walls[1] = true;
		walls[2] = true;
		walls[3] = true;
		visited = false;
	}

	public void drawCell(Graphics g) {

		int i = x * w;
		int j = y * w;
		
//		float norm = (float) ((depth % 100000) / (100000.0));
//		
//		if (depth != 0) {
//			g.setColor(Color.getHSBColor(norm,1,1));
//		}else {
//			g.setColor(Color.white);
//		}
		
		g.setColor(Color.white);
		
		
		g.fillRect(x * w,y * w,w,w);
		
		g.setColor(Color.black);
		
		if (walls[0]) { // top
			g.drawLine(i, j, i + w, j);
		}
		if (walls[1]) { // right
			g.drawLine(i + w, j, i + w, j + w);
		}
		if (walls[2]) { // bottom
			g.drawLine(i + w, j + w, i, j + w);
		}
		if (walls[3]) { // left
			g.drawLine(i, j + w, i, j);
		}

	}

	public Cell checkNeighbors() {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		
		int i = x * w;
		int j = y * w;
		
		Cell top = null;
		Cell right = null;
		Cell bottom = null;
		Cell left = null;

		if (index(x, y - 1) != -1) {
			top = Frame.grid.get(index(x, y - 1));
		}
		if (index(x + 1, y) != -1) {
			right = Frame.grid.get(index(x + 1, y));
		}
		if (index(x, y + 1) != -1) {
			bottom = Frame.grid.get(index(x, y + 1));
		}
		if (index(x - 1, x) != -1) {
			left = Frame.grid.get(index(x - 1, y));
		}

		if (top != null && !top.visited) {
			neighbors.add(top);
		}
		if (right != null && !right.visited) {
			neighbors.add(right);
		}
		if (bottom != null && !bottom.visited) {
			neighbors.add(bottom);
		}
		if (left != null && !left.visited) {
			neighbors.add(left);
		}
		
		if (neighbors.size() > 0) {
			Random rndm = new Random();
			int r = rndm.nextInt(neighbors.size());
			return neighbors.get(r);
		} else {
			return null;
		}

	}

	public void highlight(Graphics g) {
		int i = x * w;
		int j = y * w;

		g.setColor(new Color(0, 0, 255, 50));
		g.fillRect(i + 1, j + 1, w - 1, w - 1);
	}

	public int index(int x, int y) {
		if (x < 0 || y < 0 || x > Frame.cols - 1 || y > Frame.rows - 1) {
			return -1;
		}
		return x + y * Frame.cols;
	}

	public void check() {
		System.out.println("x: " + x + " y: " + y);
	}
}
