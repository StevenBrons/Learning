import java.util.ArrayList;

public class Neuron {

	ArrayList<Neuron> next = new ArrayList<>();
	ArrayList<Double> weights = new ArrayList<>();
	
	public static double WEIGHT_MIN = -0.3;
	public static double WEIGHT_MAX = 0.3;

	
	double value = 0;

	public Neuron() {
	}

	void input(double v) {
		this.value += v;
	}

	double activationFunction() {
		return 1/(1 + Math.exp(-value));
	}

	void fire() {
		double d = activationFunction();
		for (int i = 0; i < next.size(); i++) {
			next.get(i).input(d * weights.get(i));
		}
	}

	
	public void connnect(Neuron n) {
		next.add(n);
		weights.add((Math.random() * (WEIGHT_MAX - WEIGHT_MIN)) + WEIGHT_MIN);

	}

	public void clear() {
		value = 0;
	}
}
