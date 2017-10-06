import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
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
	Image image;
	JTextField title = new JTextField("Image #0");
	JTextField label = new JTextField();
	JLabel labelLabel = new JLabel("Label:");
	JButton clear = new JButton("Clear");

	Canvas imagePanel = new Canvas() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		boolean entered = false;

		double mouseX = 0;
		double mouseY = 0;

		BufferedImage screen;

		{
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
					double width = Math.min(getWidth(), getHeight());
					int drawX = (int) Math.round(mouseX * 28 / width);
					int drawY = (int) Math.round((mouseY - (getHeight() / 2 - width / 2)) * 28 / width);
					paint(drawX, drawY, 50);
					paint(drawX + 1, drawY + 1, 10);
					paint(drawX - 1, drawY - 1, 10);
					paint(drawX - 1, drawY + 1, 10);
					paint(drawX + 1, drawY - 1, 5);
					paint(drawX + 1, drawY, 10);
					paint(drawX - 1, drawY, 10);
					paint(drawX, drawY + 1, 10);
					paint(drawX, drawY - 1, 10);
					image = curInput.getImage();

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

		public void paint(int x, int y, int d) {
			if (x >= 0 && x < 28 && y >= 0 && y < 28) {
				if (curInput.data[x][y] + d < 255) {
					curInput.data[x][y] += d;
				}
			}
		}

		public void startUpdate() {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					while (entered) {
						draw();
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						repaint();
					}
				}
			});
			t.start();
		}

		public void draw() {
			Graphics g = screen.createGraphics();
			g.clearRect(0, 0, screen.getWidth(), screen.getHeight());
			int width = Math.min(getWidth(), getHeight());
			g.drawImage(image, getWidth() / 2 - width / 2, getHeight() / 2 - width / 2, width, width, null);
			g.setColor(Color.RED);
			g.fillOval((int) mouseX - 10, (int) mouseY - 10, 20, 20);
		}

		public void paint(Graphics g) {
			draw();
			g.drawImage(screen, 0, 0, null);
		}
	};

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

		gb.gridwidth = 1;
		gb.weighty = 1;
		gb.weightx = 4;
		gb.gridx = 0;
		gb.gridy = 2;
		add(labelLabel, gb);
		gb.weightx = 1;
		gb.gridx = 1;
		gb.gridy = 2;
		add(label, gb);

		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 3;
		add(clear, gb);
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
		image = curInput.getImage();
		imagePanel.repaint();
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
		double d[] = new double[curInput.data.length * curInput.data[0].length];
		for (int x = 0; x < curInput.data.length; x++) {
			for (int y = 0; y < curInput.data[x].length; y++) {
				d[x * 28 + y] = (curInput.data[x][y] / (double) 255) * 100 - 50;
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
		if (curInputNum < trainingData.size() - 2) {
			curInputNum++;
		} else {
			curInputNum = 0;
		}
		setCurInput(curInputNum);
	}

	public void prev() {
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