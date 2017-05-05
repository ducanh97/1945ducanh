package GAme;

import Model.GameRect;
import View.ImageRender;
import Controller.CollisionManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ADMIN on 4/12/2017.
 */
public class PlaneController extends Controller implements Collider {
	private GameRect gameRect;
	private ImageRender imageRender;
	private int dx = 0;
	private int dy = 0;
	private int HP;
	public int level;

	public PlaneController(int x, int y, Image image) {
		this.gameRect = new GameRect(x, y, 70, 50);
		this.imageRender = new ImageRender(image);
		this.HP = 1000;
		this.level = 1;
		CollisionManager.instance.add(this);
	}

	public GameRect getGameRect() {
		return gameRect;
	}

	@Override
	public void onCollide(Collider other) {
		if (other instanceof EnemyController) {
			((EnemyController) other).getHit(1);
		} else if (other instanceof Bullet) {
			((Bullet) other).getHit(1);
		} else if (other instanceof ExtraHP) {
			HP += 200;
			((ExtraHP) other).getHit(1);

		}
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
		getGameRect().move(dx, dy);
		dx = 0;
		dy = 0;
	}

	public void getHit(int damage) {
		HP -= damage;
		if (HP <= 0) {
			CollisionManager.instance.remove(this);
			gameRect.setDead(true);
		}
	}

	public String getHP() {
		if (HP <= 0) {
			return "You dead bitch!";
		}
		return "HP : " + HP;

	}



	public String getLevel() {
		return "Level : " +  level + "";
	}



	public void setLevelUp() {
		level +=1;
	}
}
