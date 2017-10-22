
public class Game {

	static int worldType = 3;
	public volatile boolean running = true;

	World world = new World(worldType);
	Game g = this;

	Player player = new ManualPlayer();

	Game() {
	}

	public Score run(Player p, int time) {
		running = true;
		this.player = p;
		p.reset();
		while (running) {
			try {
				player.update(g);
				Thread.sleep(time);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return player.score;
	}

}
