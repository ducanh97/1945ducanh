package GAme;

import Model.GameRect;
import View.ImageRender;
import Controller.CollisionManager;

import java.awt.*;

/**
 * Created by ADMIN on 5/2/2017.
 */
public class ExtraHP extends Controller implements Collider {
	private boolean isInvisible;

	public ExtraHP(int X, int Y, Image hPImage) {
		this.gameRect = new GameRect(X, Y, hPImage.getWidth(null), hPImage.getHeight(null));
		this.imageRender = new ImageRender(hPImage);
		this.isInvisible = true;
		CollisionManager.instance.add(this);
	}

	public void update() {
		gameRect.move(0, 2);
	}

	@Override
	public GameRect getGameRect() {
		return gameRect;
	}

	@Override
	public void onCollide(Collider other) {
		if (other instanceof PlaneController) {
			System.out.println("Cham roi");
		}
	}


	public void getHit(int damage) {
		gameRect.setDead(true);
		CollisionManager.instance.remove(this);
	}
}
