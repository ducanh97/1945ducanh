package GAme;

import Scene.GameScene;
import Scene.Level1Scene;
import Scene.MenuScene;
import Ultilities.Ultilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import Controller.CollisionManager;
import Controller.ControllerManager;

/**
 * Created by 123 on 09/04/2017.
 */
public class GameWindow extends Frame {

	Image planeImage;
	private PlaneController plane;
	private int pX;
	private int pY;
	private ArrayList<Bullet> bullets;
	//	private ArrayList<EnemyController> enemies;

	private ArrayList<EnemyController> enemies2;

	private int coolDownTime = 2000;


	private EnemyController enemy;
	public Ultilities ul;

	private int ExtraHPTime = 6000;
	private boolean haveExtraHP = false;

	private ArrayList<ExtraHP> HPList;


	private CollisionManager collisionManager;

	/**
	 *
	 */
	BufferedImage backBufferImage;
	Graphics backBufferGraphic;

	private boolean isUpPressed;
	private boolean isDownPressed;
	private boolean isLeftPressed;
	private boolean isRightPressed;
	private boolean isCooledDown = true;
	private boolean isSpacePressed = false;

	private GameScene currentScene;

	public void setCurrentScene(GameScene currentScene) {
		this.currentScene = currentScene;
	}

	public GameWindow() {
		setVisible(true);
		setSize(400, 600);

		backBufferImage = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		backBufferGraphic = backBufferImage.getGraphics();

		bullets = new ArrayList<>();
//		enemies = new ArrayList<>();

		enemies2 = new ArrayList<>();
		ul = new Ultilities();
		HPList = new ArrayList<>();
		currentScene = new MenuScene();

//		collisionManager = new CollisionManager();


		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent windowEvent) {

			}

			@Override
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}

			@Override
			public void windowClosed(WindowEvent windowEvent) {

			}

			@Override
			public void windowIconified(WindowEvent windowEvent) {

			}

			@Override
			public void windowDeiconified(WindowEvent windowEvent) {

			}

			@Override
			public void windowActivated(WindowEvent windowEvent) {

			}

			@Override
			public void windowDeactivated(WindowEvent windowEvent) {

			}
		});

		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent keyEvent) {

			}

			@Override
			public void keyPressed(KeyEvent keyEvent) {
				currentScene.keyPressed(keyEvent);
			}

			@Override
			public void keyReleased(KeyEvent keyEvent) {
				currentScene.keyReleased(keyEvent);
			}


		});


		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}


                    if(currentScene instanceof  MenuScene && !((MenuScene) currentScene).isVisible()){
						setCurrentScene(new Level1Scene());
					}
					//Move The Plane
					currentScene.run();
					;
					//Create Normal Enemy


//					for (EnemyController en : enemyController.{
//						en.update(0, 2);
					ControllerManager.instance.update();
//						if (shootingTime <= 0) {
//							try {
//								en.createBullet(Ultilities.loadImage("res/enemy_bullet.png"));
//
//							} catch (IOException e) {
//								e.printStackTrace();
//							}
//						}
//						en.shoot();
//					}



//

//					ExtraHPTime-=20;
//					 (haveExtraHP){
//						haveExtraHP = false;
//						try {
//							ExtraHP extraHP = new ExtraHP(rand.nextInt(600),0,Ultilities.loadImage("res/power-up.png"));
//						    HPList.add(extraHP);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//
//
//					if(ExtraHPTime <=0){
//						haveExtraHP = true;
//						ExtraHPTime = 6000;
//					}


					//Check va cham
					CollisionManager.instance.update();

					repaint();
				}
			}
		});

		thread.start();
	}


	@Override
	public void update(Graphics g) {
		currentScene.draw(backBufferGraphic);

		for (ExtraHP extraHP : HPList) {
			extraHP.update();
			extraHP.draw(backBufferGraphic);
		}



//		for (EnemyController enemyController : enemies) {
//			enemyController.draw(backBufferGraphic);
//			for(Bullet bullet : enemyController.getEnemyBullets()){
//				bullet.draw(backBufferGraphic);
//			}
//		}

		for (EnemyController enemyController : enemies2) {
			enemyController.draw(backBufferGraphic);
			for (Bullet bullet : enemyController.getEnemyBullets()) {
				bullet.draw(backBufferGraphic);
			}
		}


//		Iterator<EnemyController> enemyControllerIterator = enemies.iterator();
////		while (enemyControllerIterator.hasNext()) {
////			EnemyController enemyController = enemyControllerIterator.next();
////			if (enemyController.getGameRect().isDead()) {
////				enemyControllerIterator.remove();
////			}
////					}


//		for(int i =0;i<enemies.size();i++){
//			Iterator<Bullet> bulletIterator =enemies.get(i).getEnemyBullets().iterator();
//			while (bulletIterator.hasNext()) {
//				Bullet bullet = bulletIterator.next();
//				if (bullet.getGameRect().isDead()) {
//					bulletIterator.remove();
//				}
//			}
//		}

		Iterator<ExtraHP> extraHPIterator = HPList.iterator();
		while (extraHPIterator.hasNext()) {
			ExtraHP extraHP = extraHPIterator.next();
			if (extraHP.getGameRect().isDead()) {
				extraHPIterator.remove();
			}
		}

		Iterator<EnemyController> enemyControllerIterator2 = enemies2.iterator();
		while (enemyControllerIterator2.hasNext()) {
			EnemyController enemyController2 = enemyControllerIterator2.next();
			if (enemyController2.getGameRect().isDead()) {
				enemyControllerIterator2.remove();
			}
		}

		for (int i = 0; i < enemies2.size(); i++) {
			Iterator<Bullet> bulletIterator = enemies2.get(i).getEnemyBullets().iterator();
			while (bulletIterator.hasNext()) {
				Bullet bullet = bulletIterator.next();
				if (bullet.getGameRect().isDead()) {
					bulletIterator.remove();

				}
			}
		}


		g.drawImage(backBufferImage, 0, 0, null);

	}
}
