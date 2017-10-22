
public class Main {

	static EvolutionHandler evoHandler = new EvolutionHandler();
	static Frame f = new Frame();
	static Game game = new Game();

	public static void main(String args[]) {

		while (f.gameScreen.getWidth() == 0) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		f.gameScreen.init();
		f.setGame(game);

		game.run(new ManualPlayer(), 50);
	}

}
