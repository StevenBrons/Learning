import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GameScreen gameScreen = new GameScreen();
	PlayerList playerPanel = new PlayerList(Main.evoHandler);
	Settings settings = new Settings();
	ActionMenu menu = new ActionMenu();

	public Frame() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setSize(600, 400);
		setExtendedState(MAXIMIZED_BOTH);

		getContentPane().setBackground(Color.black);

		setStyle(playerPanel);
		setStyle(settings);
		setStyle(menu);
		
		JScrollPane pane = new JScrollPane(playerPanel);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setBackground(Color.BLACK);
		pane.setBorder(null);
		
		gb.gridx = 0;
		gb.gridy = 0;
		gb.weightx = 1;
		gb.weighty = 5;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridheight = 2;
		gb.ipadx = 150;
		add(pane, gb);
		gb.weightx = 4;
		gb.gridheight = 1;
		gb.gridwidth = 2;
		gb.gridx = 1;
		gb.gridy = 0;
		add(gameScreen, gb);
		gb.gridwidth = 1;
		gb.weighty = 1;
		gb.gridx = 1;
		gb.gridy = 1;
		add(menu, gb);
		gb.gridx = 2;
		gb.gridy = 1;
		add(settings, gb);
	}

	public void setGlobalStyle() {
		getContentPane().setBackground(Color.BLACK);
		for (int i = 0; i < getComponentCount(); i++) {
			setStyle((JComponent) getComponent(i));
		}
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

	public void setGame(Game g) {
		gameScreen.game = g;
	}

}
