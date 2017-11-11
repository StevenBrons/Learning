import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class OutputVisualiser extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static JTextField num = new JTextField("Guessed: \t-");
	static JTextField cer = new JTextField("Certainty: \t-");
	static JTextField cor = new JTextField("Correct: \t-");
	static JTextField av = new JTextField("Average: \t-");

	static ArrayList<Boolean> history = new ArrayList<>();
	
	static double tot = 0;
	static double good = 0;
	
	
	public OutputVisualiser() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		gb.insets = new Insets(5, 5, 5, 5);

		num.setEditable(false);
		cer.setEditable(false);
		cor.setEditable(false);
		av.setEditable(false);

		Frame.setStyle(num);
		Frame.setStyle(cer);
		Frame.setStyle(cor);
		Frame.setStyle(av);

		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridx = 0;
		gb.gridy = 0;
		add(num, gb);
		
		gb.gridx = 0;
		gb.gridy = 1;
		add(cer, gb);
		
		gb.gridx = 0;
		gb.gridy = 2;
		add(cor, gb);
		
		gb.gridx = 0;
		gb.gridy = 3;
		add(av, gb);
	}

	public static void setData(int n, double c, int t) {
		num.setText("Guessed: \t" + n);
		cer.setText("Certainty: \t" + new DecimalFormat("#.###").format(c));
		cor.setText("Correct: \t" + t);
		history.add(n == t);
		
		if (history.size() > 1000) {
			history.remove(0);
		}
		
		tot++;
		if (n == t) {
			good++;
		}

		double b = 0;
		for (boolean x: history) {
			if (x) {
				b++;
			}
		}
		Graph.dataPoints.add(b / history.size());
		av.setText("Average: \t" + new DecimalFormat("#.###").format(b / history.size()));
	}
	
}
