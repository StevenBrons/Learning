import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH / 16 * 9;

	Screen s = new Screen();
	
	CardLayout cl = new CardLayout();
	JPanel cp = new JPanel();
	
	JPanel menu = new JPanel();
	JPanel inGame = new JPanel();
	JPanel dead = new JPanel();
	JPanel infoScreen = new JPanel();
	
	int counter = 0;
	JLabel options = new JLabel("Opties:");
	JButton btnOptions = new JButton("Vlak"); 
	JButton start = new JButton("Start");
	JButton info = new JButton("Info");
	
	String overHetSpel;
	JLabel lblInfo1 = new JLabel();
	JLabel lblInfo2 = new JLabel();
	JLabel lblInfo3 = new JLabel();
	JButton back = new JButton("Back");

	JLabel lblTime = new JLabel();
	JLabel lblScore = new JLabel();

	// een frame
	public Frame() {
		// instellingen voor het frame
		setTitle("Platformer");
		setVisible(true);
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout());

		
	//menu panel	
		menu.setLayout(new GridBagLayout());
		GridBagConstraints gb = new GridBagConstraints();
		menu.setBackground(Color.WHITE);
		
		//opties label
		options.setSize(new Dimension(100, 20));
		options.setForeground(Color.BLACK);
		options.setHorizontalAlignment(JLabel.CENTER);
		options.setOpaque(true);
		options.setBackground(Color.WHITE);
		
		//opties knop
		btnOptions.setSize(new Dimension(100, 20));
		btnOptions.setForeground(Color.BLACK);
		btnOptions.setHorizontalAlignment(JLabel.CENTER);
		btnOptions.setOpaque(true);
		btnOptions.setFocusPainted(false);
		btnOptions.setBorderPainted(false);
		btnOptions.setBackground(Color.WHITE);
		
		//start knop
		start.setSize(new Dimension(100, 20));
		start.setForeground(Color.GREEN);
		start.setHorizontalAlignment(JLabel.CENTER);
		start.setOpaque(true);
		start.setFocusPainted(false);
		start.setBorderPainted(false);
		start.setBackground(Color.WHITE);
		
		//info knop
		info.setSize(new Dimension(100, 20));
		info.setForeground(Color.RED);
		info.setHorizontalAlignment(JLabel.CENTER);
		info.setOpaque(true);
		info.setFocusPainted(false);
		info.setBorderPainted(false);
		info.setBackground(Color.WHITE);
		
		gb.insets = new Insets(20, 25, 20, 25); //top left bottom right
		
		gb.gridx = 1;
		gb.gridy = 1;
		menu.add(options, gb);
		gb.gridx = 2;
		gb.gridy = 1;
		menu.add(btnOptions, gb);
		
		gb.gridx = 1;
		gb.gridy = 2;
		menu.add(info, gb);
		gb.gridx = 2;
		gb.gridy = 2;
		menu.add(start, gb);

		
	//inGame panel
		inGame.setLayout(new BorderLayout());
		
		// JLabel voor de tijd in het spel
		lblTime.setSize(new Dimension(100, 20));
		lblTime.setForeground(Color.RED);
		lblTime.setLocation(getWidth() - lblTime.getWidth(), 10);
		lblTime.setOpaque(true);
		lblTime.setBackground(s.background);

		// JLabel voor de score
		lblScore.setSize(new Dimension(100, 20));
		lblScore.setForeground(Color.GREEN);
		lblScore.setLocation(getWidth() - lblScore.getWidth(), 10 + lblTime.getHeight());
		lblScore.setOpaque(true);
		lblScore.setBackground(s.background);

		// dingen (het canvas) toevoegen aan het frame
		inGame.add(lblTime);
		inGame.add(lblScore);
		inGame.add(s, BorderLayout.CENTER);
		s.addKeyListener(new InputKeyboard());
		
		
	//dead panel
		//awef
		
	//infoScreen panel
		infoScreen.setLayout(new GridBagLayout());
		infoScreen.setBackground(Color.WHITE);
		
		//informatie label
		lblInfo1.setText("Dit spel is gemaakt door Steven Bronsveld en Thijs van Loenhout voor het praktijkonderzoek van hun profielwerkstuk.");
		lblInfo1.setHorizontalAlignment(JLabel.CENTER);
		lblInfo1.setForeground(Color.BLACK);
		lblInfo1.setOpaque(true);
		lblInfo1.setBackground(Color.WHITE);
		
		lblInfo2.setText("Kies uit verschillende wereldgeneratie opties en druk op start om het spel te starten.");
		lblInfo2.setHorizontalAlignment(JLabel.CENTER);
		lblInfo2.setForeground(Color.BLACK);
		lblInfo2.setOpaque(true);
		lblInfo2.setBackground(Color.WHITE);
		
		lblInfo3.setText("Spring met 'w', je loopt automatisch");
		lblInfo3.setHorizontalAlignment(JLabel.CENTER);
		lblInfo3.setForeground(Color.BLACK);
		lblInfo3.setOpaque(true);
		lblInfo3.setBackground(Color.WHITE);
		
		//back knop
		back.setSize(new Dimension(100, 20));
		back.setForeground(Color.RED);
		back.setHorizontalAlignment(JLabel.CENTER);
		back.setOpaque(true);
		back.setFocusPainted(false);
		back.setBorderPainted(false);
		back.setBackground(Color.WHITE);
		
		GridBagConstraints gb2 = new GridBagConstraints();
		gb2.insets = new Insets(25, 25, 25, 25);
		gb2.gridx = 1;
		gb2.gridy = 1;
		infoScreen.add(lblInfo1, gb2);
		gb2.gridx = 1;
		gb2.gridy = 2;
		infoScreen.add(lblInfo2, gb2);
		gb2.gridx = 1;
		gb2.gridy = 3;
		infoScreen.add(lblInfo3, gb2);
		gb2.gridx = 1;
		gb2.gridy = 4;
		infoScreen.add(back, gb2);
		
		
	//knop toepassingen toevoegen
		btnOptions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				counter++;
				
				switch (counter % 6) {
				case 0:
					btnOptions.setText("Vlak");
					break;
				case 1:
					btnOptions.setText("Hobbels");
					break;
				case 2:
					btnOptions.setText("Kuiltjes");
					break;
				case 3:
					btnOptions.setText("Valkuilen");
					break;
				case 4:
					btnOptions.setText("Golvend");
					break;
				case 5:
					btnOptions.setText("hard Mode");
					break;
				default:
					btnOptions.setText("Vlak");
					break;
				}
			}
		});
		
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Game.worldType = counter % 6;
				Game.running = true;
				cl.show(cp, "inGame");
				s.requestFocus();
			}
		});
		
		info.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cp, "infoScreen");				
			}
		});
		
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cl.show(cp, "menu");				
			}
		});
		
	//cardlayout
		cp.setLayout(cl);
		cp.add(menu, "menu");
		cp.add(infoScreen, "infoScreen");
		cp.add(inGame, "inGame");
		cp.add(dead, "dead");

		cl.show(cp, "menu");

		add(cp, BorderLayout.CENTER);

	}

}
