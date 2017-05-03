package Model;

import Controller.CollisionManager;

import java.awt.*;

/**
 * Created by ADMIN on 4/15/2017.
 */
public class GameRect {
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean invisible;
    private boolean isDead;

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean dead) {
		isDead = dead;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public void setInvisible(boolean invisible) {
		this.invisible = invisible;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public GameRect(int x, int y, int width, int height) {

		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void move(int dx, int dy){
		this.x += dx;
		this.y += dy;
	}

	public boolean intersects(GameRect other){

		Rectangle rectangle = new Rectangle(x,y,width,height);
		Rectangle rectangle1 = new Rectangle(other.x,other.y,other.width,other.height);

		return rectangle.intersects(rectangle1);
	}
}
