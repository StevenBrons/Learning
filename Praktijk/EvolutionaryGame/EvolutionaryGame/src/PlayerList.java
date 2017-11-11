import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerList extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerList(EvolutionHandler handler) {
		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for (int i = 0; i < handler.population.size(); i++) {
			add(new PlayerPanel(handler.population.get(i)));
		}
	}

	public void updateList(EvolutionHandler handler) {
		removeAll();
		for (int i = 0; i < handler.population.size(); i++) {
			add(new PlayerPanel(handler.population.get(i)));
		}
	}

}

class PlayerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlayerPanel(Player player) {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(Color.BLACK);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setBackground(Color.DARK_GRAY);
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						Main.game.running = false;
						
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						Main.game.run(player, 20);
					}
				});
				t.start();
			}
		});

		setBackground(Color.BLACK);
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.gridx = 0;
		gb.gridy = 0;
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		JLabel name = new JLabel(player.toString());
		name.setForeground(Color.WHITE);
		name.setFont(new Font(null, Font.BOLD, 15));
		add(name, gb);
		gb.gridx = 0;
		gb.gridy = 1;
		JLabel score = new JLabel(player.score.toString());
		score.setForeground(Color.GREEN);
		score.setFont(new Font(null, Font.ITALIC, 15));
		add(score, gb);
	}

}
