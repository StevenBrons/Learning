import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Menu() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());

		JLabel sizeLabel = new JLabel("Colmns");
		JLabel delayLabel = new JLabel("Delay");
		JLabel cellWidthLabel = new JLabel("Cell width");
		JLabel lineLabel = new JLabel("Line width");

		JTextField size = new JTextField("" + Main.SIZE);
		JTextField delay = new JTextField("" + Main.DELAY);
		JTextField cellWidth = new JTextField("" + Main.CELL_WIDTH);
		JTextField line = new JTextField("" + Main.LINE_WIDTH);

		JButton run = new JButton("Run");
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Main.f.run();
			}
		});

		Frame.setStyle(sizeLabel);
		Frame.setStyle(delayLabel);
		Frame.setStyle(cellWidthLabel);
		Frame.setStyle(lineLabel);

		Frame.setStyle(size);
		Frame.setStyle(delay);
		Frame.setStyle(cellWidth);
		Frame.setStyle(line);

		Frame.setStyle(run);

		size.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int d = Integer.parseInt(size.getText());
					Frame.setStyle(size);
					Main.SIZE = d;
				} catch (Exception ex) {
					size.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		delay.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int d = Integer.parseInt(delay.getText());
					Frame.setStyle(delay);
					Main.DELAY = d;
				} catch (Exception ex) {
					delay.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		cellWidth.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int d = Integer.parseInt(cellWidth.getText());
					Frame.setStyle(cellWidth);
					Main.CELL_WIDTH = d;
				} catch (Exception ex) {
					cellWidth.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		line.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int d = Integer.parseInt(line.getText());
					Frame.setStyle(line);
					Main.LINE_WIDTH = d;
				} catch (Exception ex) {
					line.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridx = 0;
		gb.gridy = 0;
		add(sizeLabel, gb);
		gb.gridx = 1;
		gb.gridy = 0;
		add(size, gb);

		gb.gridx = 0;
		gb.gridy = 1;
		add(delayLabel, gb);
		gb.gridx = 1;
		gb.gridy = 1;
		add(delay, gb);

		gb.gridx = 2;
		gb.gridy = 0;
		add(cellWidthLabel, gb);
		gb.gridx = 3;
		gb.gridy = 0;
		add(cellWidth, gb);
		gb.gridx = 2;
		gb.gridy = 1;
		add(lineLabel, gb);
		gb.gridx = 3;
		gb.gridy = 1;
		add(line, gb);

		gb.gridx = 0;
		gb.gridy = 2;
		gb.gridwidth = 4;
		add(run, gb);
	}

}
