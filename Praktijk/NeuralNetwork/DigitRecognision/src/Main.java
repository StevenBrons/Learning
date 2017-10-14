import java.util.Random;

public class Main {

	static Frame f = new Frame();
	static NeuralNetwork network;
	static Random r = new Random();
	static Problem problem;
	
	public static void main(String args[]) {
		new LoadFrame();
	}

	public static void run() {
		problem.run();
		Main.f.repaint();
	}

}

