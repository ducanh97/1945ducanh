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
	private ArrayList<Bullet> bullets;
	private int dx = 0;
	private int dy = 0;
    private int HP;
	public PlaneController(int x, int y, Image image, ArrayList<Bullet> bullets) {
		this.gameRect = new GameRect(x, y, 70, 50);
		this.imageRender = new ImageRender(image);
		this.bullets = bullets;
		this.HP = 1000;
		CollisionManager.instance.add(this);
	}

	public GameRect getGameRect() {
		return gameRect;
	}

	@Override
	public void onCollide(Collider other) {
    if(other instanceof EnemyController){
//		System.out.println("May bay bi ban chet");
		((EnemyController) other).getHit(1);
	} else if (other instanceof  Bullet){
		((Bullet) other).getHit(1);
	} else if(other instanceof  ExtraHP){
		HP+=200;
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

	public void getHit(int damage){
		System.out.println("May bay chet");
		HP-=damage;
		if(HP <=0) {
			CollisionManager.instance.remove(this);
			gameRect.setDead(true);
		}
	}

	public String getHP() {
		if(HP<=0){
			return "You dead bitch!";
		}
		return "HP : " + HP;

	}
}
