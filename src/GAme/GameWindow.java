package GAme;

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

/**
 * Created by 123 on 09/04/2017.
 */
public class GameWindow extends Frame {

	Image backgroundImage, planeImage;
	private PlaneController plane;
	private int pX;
	private int pY;
	private ArrayList<Bullet> bullets;
	private ArrayList<EnemyController> enemies;
	private ArrayList<EnemyController> enemies2;
	private int enemyTime = 3000;
	private int enemy2Time = 4000;
	private int coolDownTime = 2000;
	private boolean isEnemyAppear;
	private boolean isEnemy2Appear;
	private ArrayList<Bullet> enemiesBullets;
	private EnemyController enemy;
	public Ultilities ul;
	public int shootingTime = 0;
	public int shootingTime2 = 0;
	private int ExtraHPTime = 6000;
	private boolean haveExtraHP;

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

	public GameWindow() {
		setVisible(true);
		setSize(400, 600);

		backBufferImage = new BufferedImage(400, 600, BufferedImage.TYPE_INT_ARGB);
		backBufferGraphic = backBufferImage.getGraphics();

		bullets = new ArrayList<>();
		enemies = new ArrayList<>();
		enemies2 = new ArrayList<>();
		enemiesBullets = new ArrayList<>();
		ul = new Ultilities();
		HPList = new ArrayList<>();


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
		});

		try {
			backgroundImage = ImageIO.read(new File("res/background.png"));
			planeImage = ImageIO.read(new File("res/plane3.png"));
			plane = new PlaneController(200, 200, planeImage, bullets);

		} catch (IOException e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(17);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}


					//Cool Down Process
					if (!isCooledDown) {
						coolDownTime -= 17;
						if (coolDownTime <= 0) {
							coolDownTime = 800;
							isCooledDown = true;
						} else {
							isCooledDown = false;
						}
					}

					//Move The Plane
					plane.processInput(isUpPressed, isDownPressed, isLeftPressed, isRightPressed);

					//Shot Gun
					if (isSpacePressed && isCooledDown) {
						Bullet bullet = null;
						try {
							bullet = new Bullet(plane.getGameRect().getX(), plane.getGameRect().getY(), Ultilities.loadImage("res/bullet.png"),"plane");


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


					//Create Normal Enemy
					Random rand = new Random();
					int xEnemy = rand.nextInt(600);
					int yEnemy = 50;

					enemyTime -= 17;
					if (enemyTime < 0) {
						enemyTime = 1000;
						isEnemyAppear = true;
					}

					if (isEnemyAppear) {
						EnemyController enemy = null;
						try {
							enemy = new EnemyController(xEnemy, yEnemy, Ultilities.loadImage("res/enemy_plane_white_1.png"), new ArrayList<Bullet>());
							enemies.add(enemy);
						} catch (IOException e) {
							e.printStackTrace();
						}
						isEnemyAppear = false;
					}

					//Normal Enemy Shot
					if (shootingTime > 0) {
						shootingTime -= 17;
					}

					for (EnemyController en : enemies) {
						en.update(0, 2);
						if (shootingTime <= 0) {
							try {
								en.createBullet(Ultilities.loadImage("res/enemy_bullet.png"));

							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						en.shoot();
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
							enemy = new EnemyController(xEnemy2, yEnemy2, Ultilities.loadImage("res/plane1.png"), new ArrayList<Bullet>());

							enemies2.add(enemy);
						} catch (IOException e) {
							e.printStackTrace();
						}
						isEnemy2Appear = false;
					}

					//Type-2 Enemy Shot
					if (shootingTime2 > 0) {
						shootingTime2 -= 17;
					}

					for (EnemyController ens : enemies2) {
						ens.update(1, 2);
						if (shootingTime2 <= 0) {
							try {
								ens.createBullet(Ultilities.loadImage("res/bullet-single.png"));
							} catch (IOException e) {
								e.printStackTrace();
							}

						}

						ens.shoot();
					}

					if (shootingTime2 <= 0) {
						shootingTime2 = 1500;
					}
//
					ExtraHPTime-=20;

					if(ExtraHPTime <=0){
						haveExtraHP = true;
						ExtraHPTime = 6000;
					}

					if(haveExtraHP){
						haveExtraHP = false;
					ExtraHP extraHP =null;
						try {
							extraHP = new ExtraHP(rand.nextInt(600),0,Ultilities.loadImage("res/power-up.png"));
						HPList.add(extraHP);
						} catch (IOException e) {
							e.printStackTrace();
						}

					}



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
		backBufferGraphic.drawImage(backgroundImage, 0, 0, 400, 600, null);

		backBufferGraphic.drawString(plane.getHP(),50,50);
		if(!plane.getGameRect().isDead()){
			plane.draw(backBufferGraphic);
		}

		 for(ExtraHP extraHP : HPList){
			 extraHP.update();
			extraHP.draw(backBufferGraphic);
		}


		for (Bullet bullet : bullets) {
			bullet.draw(backBufferGraphic);
		}

		for (EnemyController enemyController : enemies) {
			enemyController.draw(backBufferGraphic);
			for(Bullet bullet : enemyController.getEnemyBullets()){
				bullet.draw(backBufferGraphic);
			}
		}

		for (EnemyController enemyController : enemies2) {
			enemyController.draw(backBufferGraphic);
			for(Bullet bullet : enemyController.getEnemyBullets()){
				bullet.draw(backBufferGraphic);
			}
		}


		Iterator<EnemyController> enemyControllerIterator = enemies.iterator();
		while (enemyControllerIterator.hasNext()) {
			EnemyController enemyController = enemyControllerIterator.next();
			if (enemyController.getGameRect().isDead()) {
				enemyControllerIterator.remove();
			}
					}


		for(int i =0;i<enemies.size();i++){
			Iterator<Bullet> bulletIterator =enemies.get(i).getEnemyBullets().iterator();
			while (bulletIterator.hasNext()) {
				Bullet bullet = bulletIterator.next();
				if (bullet.getGameRect().isDead()) {
					bulletIterator.remove();

				}
			}
		}

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
				Random rand = new Random();
//				if(rand.nextInt(10)==1){
//					isHeartAppear = true;
//				}
			}
		}

		for(int i =0;i<enemies2.size();i++){
			Iterator<Bullet> bulletIterator =enemies2.get(i).getEnemyBullets().iterator();
			while (bulletIterator.hasNext()) {
				Bullet bullet = bulletIterator.next();
				if (bullet.getGameRect().isDead()) {
					bulletIterator.remove();

				}
			}
		}

		Iterator<Bullet> bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (bullet.getGameRect().isDead()) {
				bulletIterator.remove();

			}
		}


		g.drawImage(backBufferImage, 0, 0, null);

	}
}
