
public class ManualPlayer extends Player {

	ManualPlayer(double i, double j, World world) {
		super(i, j, world);
		
		
		
	}

	public void input() {
		if (InputKeyboard.w) {
			this.jump = true;
		} else {
			this.jump = false;
		}
	}
	
	
}
