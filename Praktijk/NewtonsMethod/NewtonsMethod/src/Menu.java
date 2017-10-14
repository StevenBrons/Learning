import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField best = new JTextField("Best: ");
	JTextField cur = new JTextField("Current: ");

	Menu() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());

		best.setEditable(false);
		cur.setEditable(false);

		JButton changeFunctions = new JButton("Change Functions");
		
		changeFunctions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Settings();
			}
		});
		
		JButton run = new JButton("Run");
		
		run.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph.run();
			}
		});
		
		Frame.setStyle(run);
		Frame.setStyle(best);
		Frame.setStyle(cur);
		Frame.setStyle(changeFunctions);

		gb.insets = new Insets(5, 5, 5, 5);
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridx = 0;
		gb.gridy = 0;
		add(run,gb);
		gb.gridx = 1;
		gb.gridy = 0;
		add(cur,gb);
		gb.gridx = 2;
		gb.gridy = 0;
		add(best,gb);
		gb.gridx = 3;
		gb.gridy = 0;
		add(changeFunctions,gb);
	}

}
