package Controller;

import java.awt.*;
import java.io.IOException;
import java.util.*;

import GAme.Bullet;
import GAme.Controller;
import GAme.EnemyController;
import Ultilities.Ultilities;

/**
 * Created by ADMIN on 5/3/2017.
 */
public class ControllerManager {
	private ArrayList<Controller> controllers;
	private int shootingTime;
	private int shootingTime2;


	public static final ControllerManager instance = new ControllerManager();

	private ControllerManager() {
		controllers = new ArrayList<>();
	}

	public void add(Controller controller) {
		controllers.add(controller);
	}

	public void draw(Graphics graphics) {
		for (Controller controller : controllers) {
			controller.draw(graphics);
		}

		for (Controller controller : controllers) {
			if (controller instanceof EnemyController) {
				if (((EnemyController) controller).getType().equals("a")) {

					//Enemy a ban dan
					try {
						if (shootingTime > 0) {
							shootingTime -= 17;
						}

						if (shootingTime <= 0) {
							shootingTime = 1500;
							((EnemyController) controller).createBullet(Ultilities.loadImage("res/enemy_bullet.png"));
						}
						((EnemyController) controller).shoot(0, 5);
					} catch (IOException e) {
						e.printStackTrace();
					}
					for (Bullet bullet : ((EnemyController) controller).getEnemyBullets()) {
						bullet.draw(graphics);
					}
				} else if (((EnemyController) controller).getType().equals("b")) {
					//Enemy b ban dan
					try {
						if (shootingTime2 > 0) {
							shootingTime2 -= 17;
						}

						if (shootingTime2 <= 0) {
							shootingTime2 = 2000;
							((EnemyController) controller).createBullet(Ultilities.loadImage("res/bullet-single.png"));
						}
						((EnemyController) controller).shoot(0, 6);
					} catch (IOException e) {
						e.printStackTrace();
					}
					for (Bullet bullet : ((EnemyController) controller).getEnemyBullets()) {
						bullet.draw(graphics);
					}
				}
			}
		}
	}

	public void update() {
		Iterator<Controller> controllerIterator = controllers.iterator();
		while (controllerIterator.hasNext()) {
			Controller controller = controllerIterator.next();
			if (controller.getGameRect().isDead()) {
				controllerIterator.remove();
			}
		}

		//Di chuyen controller
		for (Controller controller : controllers) {
			if (controller instanceof EnemyController && ((EnemyController) controller).getType().equals("a")) {
				controller.update(0, 3);
			} else if (controller instanceof EnemyController && ((EnemyController) controller).getType().equals("b")) {
				controller.update(1, 2);
			}
		}




		//Loai bo controller da chet
		for (Controller controller : controllers) {
			if (controller instanceof EnemyController) {
				Iterator<Bullet> bulletIterator = ((EnemyController) controller).getEnemyBullets().iterator();
				while (bulletIterator.hasNext()) {
					Bullet bullet = bulletIterator.next();
					if (bullet.getGameRect().isDead()) {
						bulletIterator.remove();
					}
				}
			}
		}
	}
}
