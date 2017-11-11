import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JTextField;

class LogicGate extends Problem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField input1 = new JTextField();
	JTextField input2 = new JTextField();

	JTextField label = new JTextField("Label:");
	JTextField title = new JTextField(toString());

	public boolean curInput1 = false;
	public boolean curInput2 = false;

	public boolean gate(boolean a, boolean b) {
		return false;
	}

	public LogicGate() {
		GridBagConstraints gb = new GridBagConstraints();
		setLayout(new GridBagLayout());
		
		Frame.setStyle(title);
		Frame.setStyle(input1);
		Frame.setStyle(input2);
		Frame.setStyle(label);
		
		input1.setEditable(false);
		input2.setEditable(false);
		label.setEditable(false);
		title.setEditable(false);
		
		title.setFont(new Font(null, Font.BOLD, 50));
		input1.setFont(new Font(null, Font.BOLD, 50));
		input2.setFont(new Font(null, Font.BOLD, 50));

		gb.weightx = 1;
		gb.weighty = 1;
		gb.fill = GridBagConstraints.BOTH;

		gb.gridx = 0;
		gb.gridy = 0;
		add(title, gb);
		gb.gridx = 0;
		gb.gridy = 1;
		add(input1, gb);
		gb.gridx = 0;
		gb.gridy = 2;
		add(input2, gb);

		gb.gridx = 0;
		gb.gridy = 3;
		add(label, gb);
		next();
		updateData();
	}

	@Override
	public void run() {
		double d[] = { (curInput1 ? 1 : 0) * 100, (curInput2 ? 1 : 0) * 100 };
		Main.network.input(d, gate(curInput1, curInput2) ? 1 : 0);
	}

	@Override
	public void next() {
		curInput1 = r.nextBoolean();
		curInput2 = r.nextBoolean();
	}
	
	@Override
	public void prev() {
		next();
	}
	
	@Override
	public void updateData() {
		input1.setForeground(curInput1 ? Color.GREEN:Color.RED);
		input2.setForeground(curInput2 ? Color.GREEN:Color.RED);
		input1.setText(curInput1 + "");
		input2.setText(curInput2 + "");
		label.setText("Label: " + gate(curInput1,curInput2));
	}
	
	public int getInput() {
		return 2;
	}

	public int getOutput() {
		return 2;
	}

	@Override
	public String toString() {
		return "GATE";
	}
}