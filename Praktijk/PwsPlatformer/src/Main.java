public class Main {

	public static void main(String args[]) {
		Frame f = new Frame();

		while (f.s.getWidth() == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		Game g = new Game(true, f);
		f.s.init();
	}
}