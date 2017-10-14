import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LearnSettings extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LearnSettings() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.insets = new Insets(5, 5, 5, 5);
		
		JLabel learningRateLabel = new JLabel("Learning Rate:");
		JTextField learningRate = new JTextField(NeuralNetwork.LEARNING_RATE + "");
		JButton changeProblem = new JButton("Change problem");

		learningRate.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					double d = Double.parseDouble(learningRate.getText());
					Frame.setStyle(learningRate);
					NeuralNetwork.LEARNING_RATE = d;
				}catch (Exception ex) {
					learningRate.setBorder(BorderFactory.createLineBorder(Color.RED,4));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		changeProblem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new LoadFrame();
			}
		});
		
		Frame.setStyle(learningRateLabel);
		Frame.setStyle(learningRate);
		Frame.setStyle(changeProblem);

		setBackground(Color.BLACK);
		
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridx = 0;
		gb.gridy = 0;
		add(learningRateLabel, gb);
		gb.gridx = 1;
		gb.gridy = 0;
		add(learningRate, gb);
		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 1;
		add(changeProblem, gb);
	}
	
}
