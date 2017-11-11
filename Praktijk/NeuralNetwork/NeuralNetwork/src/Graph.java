import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Graph extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static ArrayList<Double> dataPoints = new ArrayList<>();

	public Graph() {
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		int offsetX = 10;
		int offsetY = 10;
		double width = (getWidth() - (2 * offsetX)) / (double) dataPoints.size();
		double height = (getHeight() - (2 * offsetY));

		g.setColor(Color.white);
		for (int y = 0; y < 10; y++) {
			g.drawLine(offsetX, (int) (y * (height / 10.0)), (int) getWidth() - (2 * offsetX),
					(int) (y * (height / 10.0)));
		}

		for (int i = dataPoints.size() - 1; i >= 0; i--) {
			g.setColor(Color.getHSBColor((float) (i / (double) dataPoints.size()), 1f, 1f));
			g.fillRect((int) Math.ceil(offsetX + (i * width)), (int) Math.ceil(height - (height * dataPoints.get(i))),
					(int) Math.ceil(width), (int) Math.ceil(height * dataPoints.get(i)));
		}

	}

}
