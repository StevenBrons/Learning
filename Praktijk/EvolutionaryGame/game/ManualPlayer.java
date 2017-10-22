
public class ManualPlayer extends Player {

	ManualPlayer() {
	}

	@Override
	public void input() {
		if (InputKeyboard.w) {
			this.jump = true;
		} else {
			this.jump = false;
		}
	}
	
	
}
