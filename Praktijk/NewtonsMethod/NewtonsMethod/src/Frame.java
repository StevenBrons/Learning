import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Menu menu = new Menu();
	Graph graph = new Graph();
	
	Frame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(640, 400);
		setExtendedState(MAXIMIZED_BOTH);
		getContentPane().setBackground(Color.black);
		
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setTitle("Newton's Method");
		
		setStyle(graph);
		setStyle(menu);

		gb.insets = new Insets(5, 5, 5, 5);
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridx = 0;
		gb.gridy = 0;
		add(graph, gb);
		gb.weighty = 0.05;
		gb.weightx = 1;
		gb.gridx = 0;
		gb.gridy = 1;
		add(menu, gb);
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