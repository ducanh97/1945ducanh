package GAme;

import Model.GameRect;
import View.ImageRender;
import Controller.CollisionManager;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ADMIN on 13/04/2017.
 */
public class EnemyController extends Controller implements Collider{

    private Image image;
    private  ArrayList<Bullet> enemyBullets;
    Graphics backBufferGraphic;

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public EnemyController(int x, int y, Image image, ArrayList<Bullet> enemyBullets) {
       this.gameRect = new GameRect(x,y,image.getWidth(null),image.getHeight(null));
       this.imageRender = new ImageRender(image);
       this.enemyBullets = enemyBullets;
       enemyBullets = new ArrayList<>();
       CollisionManager.instance.add(this);
    }

    public GameRect getGameRect() {
        return gameRect;
    }



    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }



    public void update(int dx, int dy){
        gameRect.move(dx,dy);
    }

    public void createBullet(Image bulletImage){
        Bullet ebullet = null;
        try {
            ebullet = new Bullet(gameRect.getX(),gameRect.getY(), bulletImage,"enemy");
//            CollisionManager.instance.add(ebullet);
        } catch (Exception e) {
            e.printStackTrace();
        }

        enemyBullets.add(ebullet);

    }

    public void shoot(){//???
        for (Bullet b : enemyBullets) {
            b.updateEnemies();
        }
    }

    public void getHit(int damage){
        System.out.println("xxx");
        gameRect.setDead(true);
        System.out.println("may bay dich chet");
        CollisionManager.instance.remove(this);
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof Bullet){
            ((Bullet)other).getHit(1);
        }
    }

}