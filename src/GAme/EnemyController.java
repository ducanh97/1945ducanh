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
    private String type;
    Graphics backBufferGraphic;

    public ArrayList<Bullet> getEnemyBullets() {
        return enemyBullets;
    }

    public void setEnemyBullets(ArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;
    }

    public EnemyController(int x, int y, Image image, ArrayList<Bullet> enemyBullets,String type) {
       this.gameRect = new GameRect(x,y,image.getWidth(null),image.getHeight(null));
       this.imageRender = new ImageRender(image);
       this.enemyBullets = enemyBullets;
       this.type = type;
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
            ebullet = new Bullet(gameRect.getX() + (gameRect.getWidth()/2),gameRect.getY() +(gameRect.getHeight()/2), bulletImage,"enemy");
        } catch (Exception e) {
            e.printStackTrace();
        }

        enemyBullets.add(ebullet);

    }

    public void shoot(int dx,int dy){//???
        for (Bullet b : enemyBullets) {
            b.getGameRect().move(dx,dy);
        }
    }

    public void getHit(int damage){
        gameRect.setDead(true);
        CollisionManager.instance.remove(this);
    }

    @Override
    public void onCollide(Collider other) {
        if(other instanceof Bullet){
            ((Bullet)other).getHit(1);
        }
    }

    public String getType() {
        return type;
    }
}