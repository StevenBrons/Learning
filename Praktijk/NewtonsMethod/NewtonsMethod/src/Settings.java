import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Settings extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static Function f = new Function("-(1/3)*Math.pow(x,3) + Math.pow(x,2) + 1");
	static Function d = new Function("-Math.pow(x,2) + 2 * x");

	public Settings() {
		setVisible(true);
		setSize(500, 220);
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		setResizable(false);
		getContentPane().setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		if (Main.f.isVisible()) {
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else {
			setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		
		setTitle("Set Functions");
		JLabel title = new JLabel("Set Functions");
		JLabel functionLabel = new JLabel("f(x) =");
		JLabel derivativeLabel = new JLabel("f'(x) = ");
		JLabel startXLabel = new JLabel("Start x-position:");
		JTextField function = new JTextField(f.f);
		JTextField derivative = new JTextField(d.f);
		JTextField startX = new JTextField("-2");
		JButton start = new JButton("Start");

		Frame.setStyle(title);
		Frame.setStyle(functionLabel);
		Frame.setStyle(derivativeLabel);
		Frame.setStyle(function);
		Frame.setStyle(derivative);
		Frame.setStyle(start);
		Frame.setStyle(startXLabel);
		Frame.setStyle(startX);
		gb.insets = new Insets(5, 5, 5, 5);
		gb.weightx = 0.5;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;
		gb.gridx = 0;
		gb.gridy = 0;
		gb.gridwidth = 2;
		add(title, gb);
		gb.gridwidth = 1;
		gb.gridx = 0;
		gb.gridy = 1;
		gb.weightx = 0.5;
		add(functionLabel, gb);
		gb.gridx = 1;
		gb.gridy = 1;
		gb.weightx = 2;
		add(function, gb);
		gb.gridx = 0;
		gb.gridy = 2;
		gb.weightx = 0.5;
		add(derivativeLabel, gb);
		gb.gridx = 1;
		gb.gridy = 2;
		gb.weightx = 2;
		add(derivative, gb);
		gb.gridx = 0;
		gb.gridy = 3;
		gb.weightx = 0.5;
		add(startXLabel, gb);
		gb.gridx = 1;
		gb.gridy = 3;
		gb.weightx = 2;
		add(startX, gb);
		gb.gridx = 0;
		gb.gridy = 4;
		gb.gridwidth = 2;
		add(start, gb);

		start.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				f = new Function(function.getText());
				d = new Function(derivative.getText());
				Frame.setStyle(function);
				Frame.setStyle(derivative);
				Frame.setStyle(startX);

				try {
					f.test(34.340);
				} catch (ScriptException e1) {
					function.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
					return;
				}
				try {
					d.test(34.340);
				} catch (ScriptException e1) {
					derivative.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
					return;
				}
				Graph.pos.clear();
				try {
					double d = Double.parseDouble(startX.getText());
					Graph.pos.add(d);
				} catch (Exception ex) {
					startX.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
					return;
				}
				Main.f.repaint();
				Main.f.setVisible(true);
				dispose();
			}
		});
		validate();
		repaint();
	}
}

class Function {

	String f;
	ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

	public Function(String str) {
		f = str;
	}

	public double test(double x) throws ScriptException {
		try {
			return (double) engine.eval("var x=34;" + "var y = " + format(f) + ";y * 1.0;");
		} catch (Exception e) {
			throw e;
		}
	}

	public String format(String fu) {
		return fu;
	}

	public double getValue(double x) {
		try {
			return (double) engine.eval("var x=" + x + ";" + "var y = " + f + ";y * 1.0;");
		} catch (Exception e) {
			return -1;
		}
	}

}
