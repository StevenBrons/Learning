
public class Game {
	
	static int worldType;
	public static volatile boolean running = false;
	
	World world = new World(worldType);
	Player player = new ManualPlayer(10, 0, world);
			
	Game (boolean UI, Frame f) {
		
		Game g = this;
		
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						f.s.drawAll(g);
						f.s.drawToScreen();
						Thread.sleep(10);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});

		Thread d = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (running) {
						try {
							f.s.countTime();
							f.lblTime.setText("Time: " + f.s.secondsRunning);
							player.update();
							f.lblScore.setText("Score: " + player.posX / 100);
							Thread.sleep(10);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}
		});

		if (UI) {
			t.start();
		}
		
		
		d.start();

	}
}
