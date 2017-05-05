package Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by ADMIN on 5/3/2017.
 */
public interface GameScene {
	void keyPressed(KeyEvent e);
	void keyReleased (KeyEvent e);
	void draw (Graphics graphics);
	void run();
}
