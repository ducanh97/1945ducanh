package Scene;

import GAme.PlaneController;
import View.ImageRender;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

/**
 * Created by ADMIN on 5/3/2017.
 */
public class MenuScene implements GameScene {
	private Image backGroundImage;
	private Image logoImage;
	private boolean isVisible = true;

	public MenuScene() {
		try {
			backGroundImage = ImageIO.read(new File("res/background.png"));
			logoImage = ImageIO.read(new File("res/star.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			isVisible = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(backGroundImage, 0, 0, 400, 600, null);
		graphics.drawImage(logoImage, 50, 150, 310, 210, null);
		graphics.drawString("Press Space to continue", 130, 450);

	}

	@Override
	public void run() {

	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean visible) {
		isVisible = visible;
	}
}
