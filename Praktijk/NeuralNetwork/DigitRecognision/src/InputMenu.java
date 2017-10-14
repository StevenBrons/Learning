import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InputMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputMenu() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.insets = new Insets(5, 5, 5, 5);

		JButton prev = new JButton("Prev");
		JButton next = new JButton("Next");
		JButton runStep = new JButton("Run (this)");
		JButton runThousand = new JButton("Run (thousand)");

		Frame.setStyle(prev);
		Frame.setStyle(next);
		Frame.setStyle(runStep);
		Frame.setStyle(runThousand);

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.f.input.next();
				Main.f.input.updateData();
			}
		});

		prev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.f.input.prev();
				Main.f.input.updateData();
			}
		});

		runStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.run();
				Main.f.nnv.setNN(Main.network);
				Main.f.input.updateData();
			}
		});
		
		runThousand.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i <= 1000; i++) {
					Main.f.input.next();
					Main.run();
				}
				Main.f.nnv.setNN(Main.network);
				Main.f.input.updateData();
			}
		});
		
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridx = 0;
		gb.gridy = 0;
		add(prev, gb);
		gb.gridx = 1;
		gb.gridy = 0;
		add(next, gb);
		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 1;
		add(runStep, gb);
		gb.gridx = 0;
		gb.gridy = 2;
		add(runThousand, gb);
	}

}
