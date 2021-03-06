import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

class DigitRecognition extends Problem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static ArrayList<TrainImage> trainingData = new ArrayList<>();
	static int curInputNum = 0;
	static TrainImage curInput;
	static Main m = new Main();
	boolean customImage = false;
	BufferedImage image;
	JTextField title = new JTextField("Image #0");
	JTextField label = new JTextField();
	JLabel labelLabel = new JLabel("Label:");
	JButton clear = new JButton("Clear");

	ImageScreen imagePanel = new ImageScreen(this);

	public DigitRecognition() {
		loadTrainingData();
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		title.setEditable(false);

		gb.insets = new Insets(5, 5, 5, 5);

		Frame.setStyle(title);
		Frame.setStyle(label);
		Frame.setStyle(labelLabel);
		Frame.setStyle(clear);

		clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				short[][] n = curInput.data;
				for (int x = 0; x < n.length; x++) {
					for (int y = 0; y < n[0].length; y++) {
						n[x][y] = 0;
					}
				}
				customImage = true;
				image = new BufferedImage(imagePanel.getWidth(), imagePanel.getHeight(), BufferedImage.TYPE_INT_ARGB);
				image.createGraphics().fillRect(0, 0, imagePanel.getWidth(), imagePanel.getHeight());
				setCurInput(curInput);
				updateData();
			}
		});

		label.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int l = Short.parseShort(label.getText());
					Frame.setStyle(label);
					curInput.label = (short) l;
				} catch (Exception ex) {
					label.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 0;
		add(title, gb);

		gb.weighty = 4;
		gb.gridx = 0;
		gb.gridy = 1;
		add(imagePanel, gb);

		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 2;
		add(clear, gb);

		gb.gridwidth = 1;
		gb.weighty = 1;
		gb.weightx = 4;
		gb.gridx = 0;
		gb.gridy = 3;
		add(labelLabel, gb);
		gb.weightx = 1;
		gb.gridx = 1;
		gb.gridy = 3;
		add(label, gb);

		if (trainingData.size() > 0) {
			setCurInput(0);
			updateData();
		}
	}

	public void loadTrainingData() {
		Thread t = new Thread(new Runnable() {
			public void run() {
				InputStream in = m.getClass().getClassLoader().getResourceAsStream("train.csv");
				Scanner s = new Scanner(in);
				s.nextLine();
				while (s.hasNextLine()) {
					trainingData.add(TrainImage.getImageFromString(s.nextLine()));
					if (curInput == null) {
						setCurInput(0);
						updateData();
					}
				}
				s.close();
			}
		});
		t.start();
	}

	public void updateData() {
		if (!customImage) {
			image = curInput.getImage();
		}
		imagePanel.updateOnce();
		label.setText("" + curInput.label);
		title.setText("Image #" + curInputNum);
	}

	public int getInput() {
		return 28 * 28;
	}

	public int getOutput() {
		return 10;
	}

	@Override
	public void run() {
		double d[] = new double[28 * 28];

		if (customImage) {
			BufferedImage resizedImage = new BufferedImage(28, 28, BufferedImage.TYPE_INT_RGB);
			resizedImage.createGraphics().drawImage(image, 0, 0, 28, 28, null);

			image = resizedImage;

			for (int x = 0; x < 28; x++) {
				for (int y = 0; y < 28; y++) {
					Color c = new Color(resizedImage.getRGB(x, y));
					int gray = 255 - (int) (((double) c.getRed() + c.getGreen() + c.getBlue()) / 3.0);
					d[x * 28 + y] = (gray / (double) 255) * 100 - 50;
					System.out.println(d[x * 28 + y]);
				}
			}

		} else {
			for (int x = 0; x < curInput.data.length; x++) {
				for (int y = 0; y < curInput.data[x].length; y++) {
					d[x * 28 + y] = (curInput.data[x][y] / (double) 255) * 100 - 50;
				}
			}
		}

		Main.network.input(d, curInput.label);
		Main.f.repaint();
	}

	public void setCurInput(TrainImage i) {
		curInput = i;
	}

	public void setCurInput(int i) {
		curInput = trainingData.get(i);
	}

	public void next() {
		customImage = false;
		if (curInputNum < trainingData.size() - 2) {
			curInputNum++;
		} else {
			curInputNum = 0;
		}
		setCurInput(curInputNum);
	}

	public void prev() {
		customImage = false;
		if (curInputNum >= 1) {
			curInputNum--;
		} else {
			curInputNum = trainingData.size() - 1;
		}
		setCurInput(curInputNum);
	}

	@Override
	public String toString() {
		return "Digit Recognition";
	}

}

class ImageScreen extends Canvas {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	boolean entered = false;

	int mouseX = 0;
	int mouseY = 0;

	BufferedImage screen;
	Graphics g2;
	DigitRecognition d;
	Graphics2D cg;

	public ImageScreen(DigitRecognition d) {
		this.d = d;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				entered = false;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				entered = true;
				startUpdate();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();

				cg = d.image.createGraphics();
				float[] fractions = { 0.1f, 0.5f, 1f };
				Color[] colors2 = { Color.BLACK, new Color(0, 0, 0, 1), new Color(0, 0, 0, 0) };
				RadialGradientPaint rgp2 = new RadialGradientPaint(new Point(mouseX, mouseY), 30,
						new Point(mouseX, mouseY), fractions, colors2, CycleMethod.NO_CYCLE);
				cg.setPaint(rgp2);
				cg.fillRect(0, 0, getWidth(), getHeight());

			}
		});

		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentResized(ComponentEvent e) {
				screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	public void updateOnce() {
		if (getWidth() > 0) {
			screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
			g2 = getGraphics();
			drawAll();
		}
	}

	@Override
	public void paint(Graphics g) {
		if (getWidth() > 0) {
			if (screen == null) {
				screen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
				g2 = getGraphics();
			}
			drawAll();
		}
	}

	public void p(int x, int y, int d) {
		if (x >= 0 && x < 28 && y >= 0 && y < 28) {
			if (DigitRecognition.curInput.data[x][y] + d < 255) {
				DigitRecognition.curInput.data[x][y] += d;
			}
		}
	}

	public void startUpdate() {
		g2 = getGraphics();
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (entered) {
					drawAll();
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	public void drawAll() {
		Graphics2D g = screen.createGraphics();
		g.clearRect(0, 0, screen.getWidth(), screen.getHeight());
		int width = Math.min(getWidth(), getHeight());
		g.drawImage(d.image, getWidth() / 2 - width / 2, getHeight() / 2 - width / 2, width, width, null);

		if (d.customImage) {
			float[] fractions = { 0.4f, 1f };
			Color[] colors = { Color.RED, new Color(0, 0, 0, 0) };
			RadialGradientPaint rgp = new RadialGradientPaint(new Point(mouseX, mouseY), 30, new Point(mouseX, mouseY),
					fractions, colors, CycleMethod.NO_CYCLE);
			g.setPaint(rgp);
			g.fillRect(0, 0, getWidth(), getHeight());
		}

		if (g2 != null) {
			g2.drawImage(screen, 0, 0, getWidth(), getHeight(), null);
		}
	}

};