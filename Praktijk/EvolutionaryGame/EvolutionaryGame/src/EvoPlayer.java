import java.awt.Color;

public class EvoPlayer extends Player {

	int DNA[];
	int generation;

	public EvoPlayer(int[] DNA, int generation) {
		img = createImage(Color.getHSBColor((float) (DNA[DNA.length - 1] / 100.0f), 1f, 1f), Color.getHSBColor((float) (DNA[DNA.length - 2] / 100.0f), 1f, 1f));
		this.generation = generation;
		this.DNA = DNA;
		t = 0;
	}

	@Override
	public void input() {
		if (pos >= DNA.length) {
			death(Main.game);
			return;
		}
		if (t == DNA[pos]) {
			this.jump = true;
			t = 0;
			pos++;
		} else {
			t++;
			this.jump = false;
		}
	}

	@Override
	public String toString() {
		return "G" + generation + " S" + DNA[DNA.length - 1];
	}

}
