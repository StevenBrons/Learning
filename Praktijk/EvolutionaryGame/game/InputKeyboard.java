import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputKeyboard implements KeyListener {

	static boolean w;
	static boolean a;
	static boolean s;
	static boolean d;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

		switch (e.getKeyCode()) {
		case 87:
			w = false;
			break;
		case 65:
			a = false;
			break;
		case 83:
			s = false;
			break;
		case 68:
			d = false;
			break;
		default:
			break;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

		switch (e.getKeyCode()) {
		case 87:
			w = true;
			s = false;
			break;
		case 65:
			a = true;
			d = false;
			break;
		case 83:
			s = true;
			w = false;
			break;
		case 68:
			d = true;
			a = false;
			break;
		default:
			break;
		}
	}
}