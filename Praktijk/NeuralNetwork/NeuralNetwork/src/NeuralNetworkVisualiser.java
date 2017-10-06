import java.awt.Color;
import java.awt.Graphics;
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
		Graphics g = nn.createGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		if (network == null) {
			return;
		}
		int startX = getWidth() / 20;
		int startY = getHeight() / 60;
		double stepY = (getHeight() - (startY * 2)) / (double) network.inputLayer.size();
		double stepY2 = (getHeight() - (startY * 2)) / (double) network.hiddenLayer.size();
		double stepY3 = (getHeight() - (startY * 2)) / (double) network.outputLayer.size();

		for (int i = 0; i < network.inputLayer.size(); i++) {
			int x1 = 0 + startX;
			int y1 = startY + ((int) (stepY * i));

			for (int i2 = 0; i2 < network.inputLayer.get(i).next.size(); i2++) {
				int x2 = getWidth() / 2;
				int y2 = startY + ((int) (stepY2 * i2));
				float weight = (float) Math.abs(network.inputLayer.get(i).weights.get(i2));
				g.setColor(Color.getHSBColor(network.inputLayer.get(i).weights.get(i2) > 0 ? 0.6f : 0f, 1f, weight));
				g.drawLine(x1, y1, x2, y2);
			}
		}

		for (int i = 0; i < network.hiddenLayer.size(); i++) {
			int x1 = getWidth() / 2;
			int y1 = startY + ((int) (stepY2 * i));

			for (int i2 = 0; i2 < network.hiddenLayer.get(i).next.size(); i2++) {
				int x2 = getWidth() - startX;
				int y2 = startY * 4 + ((int) (stepY3 * i2));
				float weight = (float) Math.abs(network.hiddenLayer.get(i).weights.get(i2));
				g.setColor(Color.getHSBColor(network.hiddenLayer.get(i).weights.get(i2) > 0 ? 0.6f : 0f, 1f, weight));
				g.drawLine(x1, y1, x2, y2);
			}
		}

		double max = Math.max(network.inputLayer.size(),
				Math.max(network.hiddenLayer.size(), network.outputLayer.size()));
		int nsize = (int) Math.max(((double) getHeight() / 2) / (max * 7),3) ;
		
		for (int i = 0; i < network.inputLayer.size(); i++) {
			int spacing = i % 5 * 10 - 30;
			int a = (int) (network.inputLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(spacing + startX - nsize, startY + ((int) (stepY * i)) - nsize, nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(spacing + startX - nsize, startY + ((int) (stepY * i)) - nsize, nsize * 2, nsize * 2);
		}

		for (int i = 0; i < network.hiddenLayer.size(); i++) {
			int a = (int) (network.hiddenLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(getWidth() / 2 - nsize, startY + ((int) (stepY2 * i)) - nsize, nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(getWidth() / 2 - nsize, startY + ((int) (stepY2 * i)) - nsize, nsize * 2, nsize * 2);
		}

		for (int j = 0; j < network.outputLayer.size(); j++) {
			double out = network.outputLayer.get(j).activationFunction();
			g.drawString(new DecimalFormat("#.###").format(out) + "", getWidth() - startX + 20,
					startY * 4 + ((int) (stepY3 * j)) + 5);
		}

		for (int i = 0; i < network.outputLayer.size(); i++) {
			int a = (int) (network.outputLayer.get(i).activationFunction() * 255);
			g.setColor(new Color(a, a, a));
			g.fillOval(getWidth() - startX - nsize, startY * 4 + ((int) (stepY3 * i)) - nsize, nsize * 2, nsize * 2);
			g.setColor(Color.white);
			g.drawOval(getWidth() - startX - nsize, startY * 4 + ((int) (stepY3 * i)) - nsize, nsize * 2, nsize * 2);
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(nn, 0, 0, null);
	}

}
