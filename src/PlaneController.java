import Model.GameRect;
import View.ImageRender;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ADMIN on 4/12/2017.
 */
public class PlaneController {
	private GameRect gameRect;
	private ImageRender imageRender;
	private ArrayList<Bullet> bullets;
	private int dx = 0;
	private int dy = 0;

	public PlaneController(int x, int y, Image image, ArrayList<Bullet> bullets) {
		gameRect = new GameRect(x, y, 70, 50);
		imageRender = new ImageRender(image);
		this.bullets = bullets;
	}

	public GameRect getGameRect() {
		return gameRect;
	}

	public void draw(Graphics graphics) {
		imageRender.render(graphics, gameRect);
	}

	public void processInput(boolean isUpPressed,
							 boolean isDownPressed,
							 boolean isLeftPressed,
							 boolean isRightPressed
							) {

		if (isUpPressed) {
			dy -= 3;
		}
		if (isDownPressed) {
			dy += 3;
		}
		if (isLeftPressed) {
			dx -= 3;
		}
		if (isRightPressed) {
			dx += 3;
		}
		getGameRect().move(dx,dy);
		dx =0;
		dy =0;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}
}
