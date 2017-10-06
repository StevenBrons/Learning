import java.util.ArrayList;

public class NeuralNetwork {

	public static int INPUT_NEURON_AMOUNT = (28 * 28);
	public static int HIDDEN_NEURON_AMOUNT = 15;
	public static int OUTPUT_NEURON_AMOUNT = 10;

	public static double LEARNING_RATE = 0.1;

	ArrayList<Neuron> inputLayer = new ArrayList<>();
	ArrayList<Neuron> hiddenLayer = new ArrayList<>();
	ArrayList<Neuron> outputLayer = new ArrayList<>();

	public NeuralNetwork() {

		for (int i = 0; i < INPUT_NEURON_AMOUNT + 1; i++) {
			inputLayer.add(new Neuron());
		}

		for (int i = 0; i < HIDDEN_NEURON_AMOUNT + 1; i++) {
			hiddenLayer.add(new Neuron());
		}

		for (int i = 0; i < OUTPUT_NEURON_AMOUNT; i++) {
			outputLayer.add(new Neuron());
		}

		for (Neuron in : inputLayer) {
			for (Neuron hn : hiddenLayer) {
				in.connnect(hn);
			}
		}

		for (Neuron hn : hiddenLayer) {
			for (Neuron on : outputLayer) {
				hn.connnect(on);
			}
		}

		// inputLayer.get(0).weights.set(0,10.0);
		// inputLayer.get(0).weights.set(1,0.0);
		// inputLayer.get(0).weights.set(2,0.0);
		//
		// inputLayer.get(1).weights.set(0,0.0);
		// inputLayer.get(1).weights.set(1,10.0);
		// inputLayer.get(1).weights.set(2,0.0);
		//
		// inputLayer.get(2).weights.set(0,0.0);
		// inputLayer.get(2).weights.set(1,0.0);
		// inputLayer.get(2).weights.set(2,0.0);

		// inputLayer.get(0).weights.set(0,0.0);
		// inputLayer.get(0).weights.set(1,1.2);
		//
		// inputLayer.get(1).weights.set(0,0.0);
		// inputLayer.get(1).weights.set(1,1.2);
		//
		// inputLayer.get(2).weights.set(0,1.3);
		// inputLayer.get(2).weights.set(1,0.0);
		//
		// hiddenLayer.get(0).weights.set(0, 1.0);
		// hiddenLayer.get(1).weights.set(1, 1.0);
	}

	public void gradientDescent(int tag) {

		for (int a = 0; a < hiddenLayer.size(); a++) {
			double h = hiddenLayer.get(a).activationFunction();
			double tot = 0;

			for (int j = 0; j < hiddenLayer.get(a).next.size(); j++) {
				double Who = hiddenLayer.get(a).weights.get(j);
				double y = hiddenLayer.get(a).next.get(j).activationFunction();
				double t = hiddenLayer.get(a).next.get(j).equals(outputLayer.get(tag)) ? 1 : 0;
				double dA = (y - t) * y * (1 - y);
				double Ew2 = dA * h;
				tot += (dA * Who);
				hiddenLayer.get(a).weights.set(j, Who - (LEARNING_RATE * Ew2));
			}

			for (int k = 0; k < inputLayer.size(); k++) {
				double Wih = inputLayer.get(k).weights.get(a);
				double Ew1 = tot * h * (1 - h) * inputLayer.get(k).activationFunction();
				inputLayer.get(k).weights.set(a, Wih - (LEARNING_RATE * Ew1));
			}
		}
	}

	public void input(double[] inputs, int tag) {
		for (Neuron n : inputLayer) {
			n.clear();
		}
		for (Neuron n : hiddenLayer) {
			n.clear();
		}
		for (Neuron n : outputLayer) {
			n.clear();
		}
		for (int i = 0; i < inputs.length; i++) {
			inputLayer.get(i).input(inputs[i]);
		}
		inputLayer.get(inputLayer.size() - 1).input(10000); // bias
		hiddenLayer.get(hiddenLayer.size() - 1).input(10000); // bias

		for (Neuron in : inputLayer) {
			in.fire();
		}
		for (Neuron hn : hiddenLayer) {
			hn.fire();
		}
		double certainty = -1;
		int num = -1;
		for (int j = 0; j < outputLayer.size(); j++) {
			double out = outputLayer.get(j).activationFunction();
			if (out > certainty) {
				certainty = out;
				num = j;
			}
		}
		OutputVisualiser.setData(num, certainty, tag);
		gradientDescent(tag);
	}

	// double tot = 0;
	// for (int i = 0; i < hiddenLayer.size(); i++) {
	// double h = hiddenLayer.get(i).activationFunction();
	// for (int j = 0; j < hiddenLayer.get(i).weights.size(); j++) {
	// double oldWeightHO = hiddenLayer.get(i).weights.get(j);
	// double output = hiddenLayer.get(i).next.get(j).activationFunction();
	// double tag = hiddenLayer.get(i).next.get(j).equals(outputLayer.get(t)) ?
	// 1 : 0;
	// double newWeight = oldWeightHO - LEARNING_RATE * ((output - tag) * output
	// * (1 - output) * h);
	// hiddenLayer.get(i).weights.set(j, newWeight);
	//
	// tot += ((output - tag) * output * (1 - output) * newWeight);
	// }
	// }
	// for (int i = 0; i < inputLayer.size(); i++) {
	// for (int j = 0; j < inputLayer.get(i).next.size(); j++) {
	// double oldWeightIH = inputLayer.get(i).weights.get(j);
	// double h = inputLayer.get(i).next.get(j).activationFunction();
	// double newWeightIH = oldWeightIH - LEARNING_RATE * tot * h * (1 - h) *
	// inputLayer.get(i).activationFunction();
	// inputLayer.get(i).weights.set(j, newWeightIH);
	// }
	// }

}
