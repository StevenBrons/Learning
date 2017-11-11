import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class EvolutionHandler {

	ArrayList<EvoPlayer> population = new ArrayList<>();
	public static int POPULATION_SIZE = 1000;
	public static final int DNA_SIZE = 300;

	public static double MUTATION_RATE = 0.05;
	public static double MUTATION_AMOUNT = 1;
	public static double KILL_PERCENT = 0.2;

	Random r = new Random();

	public EvolutionHandler() {
	}

	public int[] randomDNA() {
		int[] DNA = new int[DNA_SIZE];
		for (int i = 0; i < DNA_SIZE; i++) {
			DNA[i] = r.nextInt(100);
		}
		DNA[DNA_SIZE - 1] = r.nextInt(1000);
		DNA[DNA_SIZE - 2] = r.nextInt(1000);

		return DNA;
	}

	public void runAll() {
		for (Player player : population) {
			Main.game.run(player, 0);
		}
		sort();
		Main.f.playerPanel.updateList(this);
	}

	public EvoPlayer weightedRandom(ArrayList<EvoPlayer> old) {
		Random r = new Random();
		int tot = 0;
		for (EvoPlayer p : old) {
			tot += p.score.getFitness();
		}
		int i = r.nextInt(tot);
		int i2 = 0;
		for (EvoPlayer p : old) {
			i2 += p.score.getFitness();
			if (i2 > i) {
				return p;
			}
		}
		return old.get(0);

	}

	public int[] crossover(int[] a, int[] b) {
		int n = r.nextInt(DNA_SIZE);
		int[] newdna = new int[DNA_SIZE];

		for (int i = 0; i < DNA_SIZE; i++) {
			if (i < n) {
				newdna[i] = a[i];
			} else {
				newdna[i] = b[i];
			}
		}
		return newdna;
	}

	public void repopulate() {
		while (population.size() < POPULATION_SIZE) {
			population.add(new EvoPlayer(randomDNA(), 0));
		}

		ArrayList<EvoPlayer> old = new ArrayList<>(population);
		population.clear();

		while (population.size() < POPULATION_SIZE) {
			EvoPlayer p1 = weightedRandom(old);
			EvoPlayer p2 = weightedRandom(old);

			int[] newdna = crossover(p1.DNA, p2.DNA);
			population.add(new EvoPlayer(mutate(newdna), p1.generation + 1));
		}
	}

	public EvoPlayer reproduce(EvoPlayer p) {

		EvoPlayer p2 = new EvoPlayer(mutate(p.DNA), p.generation + 1);
		return p2;
	}

	public int[] mutate(int[] d) {
		Random r = new Random();
		for (int i = 0; i < DNA_SIZE; i++) {
			if (Math.random() < MUTATION_RATE) {
				d[r.nextInt(DNA_SIZE - 2)] = r.nextInt(50);
			}
		}
		return d;
	}

	public void sort() {
		population.sort(new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return (int) (o2.score.getFitness() - o1.score.getFitness());
			}
		});
	}
}
