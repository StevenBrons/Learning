import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class LoadFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadFrame() {
		setVisible(true);
		if (Main.f.isVisible()) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		} else {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		setTitle("Load Problem");
		setLocationRelativeTo(null);
		setSize(400, 220);
		setResizable(false);
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.BLACK);

		JLabel loadLabel = new JLabel("Load problem:");
		Problem[] p = { new DigitRecognition(), new AND(), new OR(), new XOR() };
		JComboBox<Problem> problems = new JComboBox<>(p);

		JLabel hiddenLabel = new JLabel("Hidden neurons:");
		JTextField hidden = new JTextField("15");
		JLabel startWeightsLabel = new JLabel("Start weights:");
		JTextField startWeightsMin = new JTextField("-0.3");
		JTextField startWeightsMax = new JTextField("0.3");
		JButton start = new JButton("Start");

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Problem p = (Problem) problems.getSelectedItem();
				NeuralNetwork.INPUT_NEURON_AMOUNT = p.getInput();
				NeuralNetwork.OUTPUT_NEURON_AMOUNT = p.getOutput();
				Main.problem = p;

				try {
					Main.f.remove(Main.f.input);
				}catch (Exception ex) {
				}
				
				Frame.setStyle(p);
				Main.f.input = p;
				Main.f.gb.gridx = 0;
				Main.f.gb.gridy = 0;
				Main.f.add(p, Main.f.gb);

				Frame.setStyle(hidden);
				Frame.setStyle(startWeightsMin);
				Frame.setStyle(startWeightsMax);
				try {
					int h = Integer.parseInt(hidden.getText());
					if (h < 1) {
						throw new Exception();
					}
					NeuralNetwork.HIDDEN_NEURON_AMOUNT = h;
				} catch (Exception ex) {
					hidden.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					return;
				}
				double min = 1;
				try {
					min = Double.parseDouble(startWeightsMin.getText());
				} catch (Exception ex) {
					startWeightsMin.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					return;

				}
				double max = 0;
				try {
					max = Double.parseDouble(startWeightsMax.getText());
				} catch (Exception ex) {
					startWeightsMax.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					return;
				}
				Neuron.WEIGHT_MAX = max;

				if (min > max) {
					startWeightsMax.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					startWeightsMin.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
					return;
				}
				Neuron.WEIGHT_MIN = min;

				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				Main.network = new NeuralNetwork();
				Main.f.nnv.setNN(Main.network);
				Main.f.setVisible(true);

				OutputVisualiser.good = 0;
				OutputVisualiser.tot = 0;
				OutputVisualiser.history.clear();
				OutputVisualiser.setData(0, 0, 0);
				Graph.dataPoints.clear();

				dispose();

			}
		});

		Frame.setStyle(loadLabel);
		Frame.setStyle(problems);
		Frame.setStyle(hiddenLabel);
		Frame.setStyle(hidden);
		Frame.setStyle(startWeightsLabel);
		Frame.setStyle(startWeightsMin);
		Frame.setStyle(startWeightsMax);
		Frame.setStyle(start);

		gb.insets = new Insets(5, 5, 5, 5);
		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridwidth = 2;
		gb.gridx = 0;
		gb.gridy = 0;
		add(loadLabel, gb);
		gb.gridx = 2;
		gb.gridy = 0;
		add(problems, gb);

		gb.gridx = 0;
		gb.gridy = 1;
		add(hiddenLabel, gb);
		gb.gridx = 2;
		gb.gridy = 1;
		add(hidden, gb);

		gb.gridx = 0;
		gb.gridy = 2;
		gb.gridwidth = 1;
		add(startWeightsLabel, gb);
		gb.gridx = 2;
		gb.gridy = 2;
		add(startWeightsMin, gb);
		gb.gridx = 3;
		gb.gridy = 2;
		add(startWeightsMax, gb);
		gb.gridx = 0;
		gb.gridy = 3;
		gb.gridwidth = 4;
		add(start, gb);

		validate();
	}

}
