package Scene;

import Controller.ControllerManager;
import GAme.Bullet;
import GAme.EnemyController;
import GAme.PlaneController;
import Ultilities.Ultilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by ADMIN on 5/3/2017.
 */
public class Level1Scene implements GameScene {
	BufferedImage backBufferImage;
	Graphics backBufferGraphic;
	private Image backGroundImage;
	private PlaneController planeController;
	private int levelUpStatus;

	private int enemyTime = 3000;
	private int enemy2Time = 4000;
	public int shootingTime = 0;
	public int shootingTime2 = 0;

	private boolean isEnemyAppear;
	private boolean isEnemy2Appear;
	private boolean isUpPressed;
	private boolean isDownPressed;
	private boolean isLeftPressed;
	private boolean isRightPressed;
	private boolean isSpacePressed;
	private boolean isCooledDown;
	private int coolDownTime;
	private ArrayList<Bullet> bullets;

	private Image planeImage;

	public Level1Scene() {
		try {
			backGroundImage = ImageIO.read(new File("res/background.png"));
			planeImage = ImageIO.read(new File("res/plane3.png"));
			planeController = new PlaneController(200, 200, planeImage);

		} catch (IOException e) {
			e.printStackTrace();
		}

		bullets = new ArrayList<>();
	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			isRightPressed = true;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			isLeftPressed = true;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			isDownPressed = true;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			isUpPressed = true;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			isSpacePressed = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
			isRightPressed = false;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
			isLeftPressed = false;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
			isDownPressed = false;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
			isUpPressed = false;
		} else if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
			isSpacePressed = false;
		}
	}

	public void run() {
		planeController.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed);
		shoot();
		if (levelUpStatus == 10) {
			planeController.setLevelUp();
			levelUpStatus = 0;
		}

		Random rand = new Random();
		int xEnemy = rand.nextInt(600);
		int yEnemy = 0;

		enemyTime -= 17;
		if (enemyTime < 0) {
			enemyTime = 1000;
			isEnemyAppear = true;
		}


		if (isEnemyAppear) {
			EnemyController enemy = null;
			try {
				enemy = new EnemyController(xEnemy, yEnemy, Ultilities.loadImage("res/enemy_plane_white_1.png"), new ArrayList<Bullet>(), "a");

				ControllerManager.instance.add(enemy);
			} catch (IOException e) {
				e.printStackTrace();
			}
			isEnemyAppear = false;
		}

		//Normal Enemy Shot
		if (shootingTime > 0) {
			shootingTime -= 17;
		}

		if (shootingTime <= 0) {
			shootingTime = 1500;
		}

		//Create Type-2 Enemy

		int xEnemy2 = rand.nextInt(600);
		int yEnemy2 = 0;
//
		enemy2Time -= 17;
		if (enemy2Time < 0) {
			enemy2Time = 1000;
			isEnemy2Appear = true;
		}

		if (isEnemy2Appear) {
			EnemyController enemy = null;
			try {
				enemy = new EnemyController(xEnemy2, yEnemy2, Ultilities.loadImage("res/plane1.png"), new ArrayList<Bullet>(), "b");
				ControllerManager.instance.add(enemy);
			} catch (IOException e) {
				e.printStackTrace();
			}
			isEnemy2Appear = false;
		}

	}

	@Override
	public void draw(Graphics graphics) {
		graphics.drawImage(backGroundImage, 0, 0, 400, 600, null);
		graphics.drawString(planeController.getHP(), 50, 50);

		graphics.drawString(planeController.getLevel(), 300, 50);
		if (!planeController.getGameRect().isDead()) {
			planeController.draw(graphics);
		}
		ControllerManager.instance.draw(graphics);
		for (Bullet bullet : bullets) {
			bullet.draw(graphics);
		}

		Iterator<Bullet> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (bullet.getGameRect().isDead()) {
				levelUpStatus += 1;
				bulletIterator.remove();

			}
		}


	}

	public void shoot() {

		//Cool Down Process
		if (!isCooledDown) {
			coolDownTime -= 50;
			if (coolDownTime <= 0) {
				coolDownTime = 800;
				isCooledDown = true;
			} else {
				isCooledDown = false;
			}
		}

		//Shot Gun
		if (isSpacePressed && isCooledDown) {
			Bullet bullet = null;
			try {

				if(planeController.level >1 && planeController.level<3){
					bullet = new Bullet(planeController.getGameRect().getX(), planeController.getGameRect().getY(), Ultilities.loadImage("res/bullet-double.png"), "plane");
				} else if(planeController.level >=3) {

					Bullet bullet1 = new Bullet(planeController.getGameRect().getX(),planeController.getGameRect().getY(),Ultilities.loadImage("res/bullet-left.png"),"plane-right");
					Bullet bullet2 = new Bullet(planeController.getGameRect().getX(),planeController.getGameRect().getY(),Ultilities.loadImage("res/bullet-right.png"),"plane-left");
					bullet = new Bullet(planeController.getGameRect().getX(), planeController.getGameRect().getY(), Ultilities.loadImage("res/bullet-up.png"), "plane");
					bullets.add(bullet1);
					bullets.add(bullet2);
				}else
				{
					bullet = new Bullet(planeController.getGameRect().getX(), planeController.getGameRect().getY(), Ultilities.loadImage("res/bullet.png"), "plane");
				}
			} catch (IOException e) {
				e.printStackTrace();
 			}
			bullets.add(bullet);
			isCooledDown = false;
		}
		for (Bullet bullet : bullets) {
			bullet.update();
		}
		isSpacePressed = false;

	}
}
