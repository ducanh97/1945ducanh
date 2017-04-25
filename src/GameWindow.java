import Model.GameRect;
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
import java.util.Random;

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
	public ArrayList planeList;
	public ArrayList<Bullet> bulletList;

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

		planeList = new ArrayList();
		bulletList = new ArrayList();

		for (EnemyController e : enemies) {
			bulletList.addAll(e.getEnemyBullets());
		}

		for (EnemyController e : enemies2) {
			bulletList.addAll(e.getEnemyBullets());
		}


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
							bullet = new Bullet(plane.getGameRect().getX(), plane.getGameRect().getY(), Ultilities.loadImage("res/bullet.png"));
							System.out.println("Cos bullet");

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
					int yEnemy = 0;

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
							System.out.println("Enemy type-2 is here");
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
							System.out.println("Type 2 pentakill");
						}

						ens.shoot();
					}

					if (shootingTime2 <= 0) {
						shootingTime2 = 1500;
					}

					for (int i = 0; i < bullets.size(); i++) {
						for (int j = 0; j < enemies.size(); j++) {
							if (Ultilities.isHitted(bullets.get(i).getGameRect(), enemies.get(j).getGameRect())) {
								enemies.remove(j);
								bullets.remove(i);
								System.out.println("Chet me may roi");
								i--;
								j--;

							}
						}

						for (int j = 0; j < enemies2.size(); j++) {
							if (Ultilities.isHitted(bullets.get(i).getGameRect(), enemies2.get(j).getGameRect())) {
								enemies2.remove(j);
								bullets.remove(i);
								System.out.println("Chet me may roi");
								i--;
								j--;

							}
						}
					}
					for (int i = 0; i < enemies.size(); i++) {
						for (int j = 0; j < enemies.get(i).getEnemyBullets().size(); j++) {
							if (Ultilities.isHitted(enemies.get(i).getEnemyBullets().get(j).getGameRect(), plane.getGameRect())) {
								enemies.get(i).getEnemyBullets().remove(j);
								System.out.println("CHETTTTTTTTTTTT");
							}
						}
					}


					repaint();
				}
			}
		});

		thread.start();
	}


	@Override
	public void update(Graphics g) {
		backBufferGraphic.drawImage(backgroundImage, 0, 0, 400, 600, null);
		plane.draw(backBufferGraphic);
		for (Bullet bullet : bullets) {
			bullet.draw(backBufferGraphic);

		}
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(backBufferGraphic);
			for (int j = 0; j < enemies.get(i).getEnemyBullets().size(); j++) {
				enemies.get(i).getEnemyBullets().get(j).draw(backBufferGraphic);
			}

		}

		for (int i = 0; i < enemies2.size(); i++) {
			enemies2.get(i).draw(backBufferGraphic);
			for (int j = 0; j < enemies2.get(i).getEnemyBullets().size(); j++) {
				enemies2.get(i).getEnemyBullets().get(j).draw(backBufferGraphic);
			}

		}


		g.drawImage(backBufferImage, 0, 0, null);
	}
}
