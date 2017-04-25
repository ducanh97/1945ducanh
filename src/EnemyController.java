import Model.GameRect;
import Ultilities.Ultilities;
import View.ImageRender;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ADMIN on 13/04/2017.
 */
public class EnemyController {
    private GameRect gameRect;
    private ImageRender imageRender;
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
       gameRect = new GameRect(x,y,image.getWidth(null),image.getHeight(null));
       imageRender = new ImageRender(image);
        this.enemyBullets = enemyBullets;
        enemyBullets = new ArrayList<>();
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

    public void draw(Graphics graphics) {
        imageRender.render(graphics,gameRect);
    }

    public void update(int dx, int dy){
        gameRect.move(dx,dy);
    }
    public void createBullet(Image bulletImage){
        Bullet ebullet = null;
        try {
            ebullet = new Bullet(gameRect.getX(),gameRect.getY(), bulletImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        enemyBullets.add(ebullet);

    }

    public void shoot(){
        for (Bullet b : enemyBullets) {
            b.updateEnemies();
        }




    }


}