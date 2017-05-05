package GAme;

import Model.GameRect;
import View.ImageRender;

import java.awt.*;

/**
 * Created by ADMIN on 4/26/2017.
 */
public class Controller {
	protected GameRect gameRect;
	protected ImageRender imageRender;

	public Controller() {
	}

	public Controller(GameRect gameRect, ImageRender imageRender) {
		this.gameRect = gameRect;
		this.imageRender = imageRender;
	}

	public void draw(Graphics graphics){
		if(gameRect.isInvisible()) return;
		imageRender.render(graphics,gameRect);
	}

	public GameRect getGameRect() {
		return gameRect;
	}

	public void update(int dx,int dy){
        getGameRect().move(dx,dy);
	}
}
