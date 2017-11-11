import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Problem input;
	NeuralNetworkVisualiser nnv = new NeuralNetworkVisualiser();
	OutputVisualiser output = new OutputVisualiser();
	Graph graph = new Graph();
	LearnSettings learn = new LearnSettings();
	GridBagConstraints gb = new GridBagConstraints();
	InputMenu menu = new InputMenu();

	public Frame() {
		setSize(640, 400);
		setTitle("Digit Recognition");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		
		setLayout(new GridBagLayout());
		getContentPane().setBackground( Color.BLACK );

		setStyle(output);
		setStyle(menu);
		setStyle(graph);
		setStyle(nnv);
		setStyle(learn);

		gb.weightx = 1;
		gb.weighty = 1;

		gb.fill = GridBagConstraints.BOTH;



		gb.gridx = 0;
		gb.gridy = 1;
		add(menu, gb);

		gb.weightx = 10;
		gb.weighty = 5;
		gb.gridx = 1;
		gb.gridy = 0;
		add(nnv, gb);

		gb.weighty = 1;
		gb.gridx = 1;
		gb.gridy = 1;
		add(graph, gb);

		gb.weightx = 1;
		gb.gridx = 2;
		gb.gridy = 0;
		add(output, gb);

		gb.gridx = 2;
		gb.gridy = 1;
		add(learn, gb);

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

}
