package GAme;

import Model.GameRect;
import Controller.CollisionManager;
import View.ImageRender;

import java.awt.*;

public class Bullet extends Controller implements Collider {
	private int damage = 100;
	private String type;


	public Bullet(int bulletX, int bulletY, Image bulletImage, String type) {
		this.gameRect = new GameRect(bulletX, bulletY, bulletImage.getWidth(null), bulletImage.getHeight(null));
		this.imageRender = new ImageRender(bulletImage);
		this.type = type;
		CollisionManager.instance.add(this);
	}

	public GameRect getGameRect() {
		return gameRect;
	}

	@Override
	public void onCollide(Collider other) {
		if (other instanceof EnemyController) {
			((EnemyController) other).getHit(damage);
		} else if (other instanceof PlaneController) {
			((PlaneController) other).getHit(damage);
		}
	}

	public void getHit(int damage) {
		gameRect.setDead(true);
		CollisionManager.instance.remove(this);
	}

	public void update() {
		if (type.equals("plane-left")) {
			gameRect.move(-2, -2);
		} else if (type.equals("plane-right")) {
			gameRect.move(2, -2);
		} else {
			gameRect.move(0, -5);
		}
	}

	public void updateEnemies() {
		gameRect.move(0, 5);
	}

	public String getType() {
		return type;
	}
}

