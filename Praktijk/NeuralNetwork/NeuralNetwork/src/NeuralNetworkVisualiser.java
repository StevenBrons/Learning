import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.swing.JPanel;

public class NeuralNetworkVisualiser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NeuralNetwork network;
	BufferedImage nn;

	public NeuralNetworkVisualiser() {
		setBackground(Color.black);
		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				nn = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				drawNN();
				repaint();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public void setNN(NeuralNetwork n) {
		this.network = n;
		drawNN();
		repaint();
	}

	public void drawNN() {
		if (nn == null) {
			return;
		}
		Graphics2D g = nn.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.scale(0.95, 0.95);
		g.translate(getWidth() * 0.025, getHeight() * 0.025);

		if (network == null) {
			return;
		}

		int startX = 50;
		double stepY1 = getHeight() / (network.inputLayer.size() + 1.0);
		double stepY2 = getHeight() / (network.hiddenLayer.size() + 1.0);
		double stepY3 = getHeight() / (network.outputLayer.size() + 1.0);
		double max = Math.max(network.inputLayer.size(),
				Math.max(network.hiddenLayer.size(), network.outputLayer.size()));
		int nsize = (int) Math.max(((double) getHeight() / 2) / (max * 7), 3);

		g.setStroke(new BasicStroke(getHeight() / (10 * Math.max(network.inputLayer.size(), network.hiddenLayer.size()))));

		for (int i = 0; i < network.inputLayer.size(); i++) {
			int x1 = startX - nsize;
			int y1 = (int) ((stepY1 * i) + stepY1);

			for (int i2 = 0; i2 < network.inputLayer.get(i).next.size(); i2++) {
				int x2 = getWidth() / 2;
				int y2 = (int) ((stepY2 * i2) + stepY2);
				float weight = (float) Math.abs(network.inputLayer.get(i).weights.get(i2));
				g.setColor(Color.getHSBColor(network.inputLayer.get(i).weights.get(i2) > 0 ? 0.6f : 0f, 1f, weight));
				g.drawLine(x1, y1, x2, y2);
			}
		}

		for (int i = 0; i < network.hiddenLayer.size(); i++) {
			int x1 = getWidth() / 2;
			int y1 = (int) ((stepY2 * i) + stepY2);

			for (int i2 = 0; i2 < network.hiddenLayer.get(i).next.size(); i2++) {
				int x2 = getWidth() - startX;
				int y2 = (int) ((stepY3 * i2) + stepY3);
				float weight = (float) Math.abs(network.hiddenLayer.get(i).weights.get(i2));
				g.setColor(Color.getHSBColor(network.hiddenLayer.get(i).weights.get(i2) > 0 ? 0.6f : 0f, 1f, weight));
				g.drawLine(x1, y1, x2, y2);
			}
		}

		for (int i = 0; i < network.inputLayer.size(); i++) {
			int spacing = network.inputLayer.size() > 50 ? i % 5 * 10 - 30 : 0;
			int a = (int) (network.inputLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(spacing + startX - nsize, (int) ((stepY1 * i) - nsize + stepY1), nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(spacing + startX - nsize, (int) ((stepY1 * i) - nsize + stepY1), nsize * 2, nsize * 2);
		}

		for (int i = 0; i < network.hiddenLayer.size(); i++) {
			int a = (int) (network.hiddenLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(getWidth() / 2 - nsize, (int) ((stepY2 * i) - nsize + stepY2), nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(getWidth() / 2 - nsize, (int) ((stepY2 * i) - nsize + stepY2), nsize * 2, nsize * 2);
		}

		for (int j = 0; j < network.outputLayer.size(); j++) {
			double out = network.outputLayer.get(j).activationFunction();
			g.drawString(new DecimalFormat("#.###").format(out) + "", getWidth() - startX + nsize + 5,
					(int) ((stepY3 * j) + 5 + stepY3));
		}

		for (int i = 0; i < network.outputLayer.size(); i++) {
			int a = (int) (network.outputLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(getWidth() - startX - nsize, (int) ((stepY3 * i) - nsize + stepY3), nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(getWidth() - startX - nsize, (int) ((stepY3 * i) - nsize + stepY3), nsize * 2, nsize * 2);
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(nn, 0, 0, null);
	}

}
