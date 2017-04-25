import Model.GameRect;
import View.ImageRender;

import java.awt.*;

public class Bullet {
    private GameRect gameRect;
    private Image bulletImage;
    private ImageRender imageRender;


    public Bullet(int bulletX, int bulletY, Image bulletImage) {
      gameRect = new GameRect(bulletX,bulletY,bulletImage.getWidth(null),bulletImage.getHeight(null));
      imageRender  = new ImageRender(bulletImage);
    }

    public GameRect getGameRect() {
        return gameRect;
    }

    public Image getBulletImage() {
        return bulletImage;
    }

    public void draw(Graphics graphics) {
      imageRender.render(graphics,gameRect);
    }

    public void update() {
       gameRect.move(0,-5);
    }

    public void updateEnemies(){
        gameRect.move(0,5);
    }
}

