import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Settings extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Settings() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		JLabel mutationRate = new JLabel("Mutation rate (%)");
		JTextField rate = new JTextField("" + EvolutionHandler.MUTATION_RATE);
		JLabel populationSize = new JLabel("Population size");
		JTextField pop = new JTextField("" + EvolutionHandler.POPULATION_SIZE);
		
		rate.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					double d = Double.parseDouble(rate.getText());
					Frame.setStyle(rate);
					EvolutionHandler.MUTATION_RATE = d;
				}catch (Exception ex) {
					rate.setBorder(BorderFactory.createLineBorder(Color.RED,4));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		pop.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				try {
					int d = Integer.parseInt(pop.getText());
					Frame.setStyle(pop);
					EvolutionHandler.POPULATION_SIZE = d;
				}catch (Exception ex) {
					pop.setBorder(BorderFactory.createLineBorder(Color.RED,4));
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		Frame.setStyle(mutationRate);
		Frame.setStyle(rate);
		Frame.setStyle(populationSize);
		Frame.setStyle(pop);
		
		gb.gridx = 0;
		gb.gridy = 0;
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		add(mutationRate, gb);
		gb.gridx = 1;
		gb.gridy = 0;
		add(rate, gb);
		gb.gridx = 0;
		gb.gridy = 1;
		add(populationSize, gb);
		gb.gridx = 1;
		gb.gridy = 1;
		add(pop, gb);
	}
	
}
