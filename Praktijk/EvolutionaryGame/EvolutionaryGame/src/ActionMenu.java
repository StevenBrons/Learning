import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ActionMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	boolean r = false;

	public ActionMenu() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());

		JButton run = new JButton("Repopulate");
		run.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (r) {
					Main.evoHandler.runAll();
					run.setText("Repopulate");
				} else {
					Main.evoHandler.repopulate();
					run.setText("Run");
				}
				r = !r;
				Main.f.playerPanel.updateList(Main.evoHandler);
				Main.f.validate();
				Main.f.repaint();
			}
		});

		JButton runTen = new JButton("Run  Ten");
		runTen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < (r?21:20); i++) {
					if (r) {
						Main.evoHandler.runAll();
					} else {
						Main.evoHandler.repopulate();
					}
					r = !r;
				}
				Main.f.playerPanel.updateList(Main.evoHandler);
				Main.f.validate();
				Main.f.repaint();
			}
		});

		Frame.setStyle(run);
		Frame.setStyle(runTen);

		gb.gridx = 0;
		gb.gridy = 0;
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		add(run, gb);
		gb.gridx = 0;
		gb.gridy = 1;
		add(runTen, gb);
	}

}
