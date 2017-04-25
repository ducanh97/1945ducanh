package View;

import Model.GameRect;
import Ultilities.Ultilities;
import jdk.nashorn.api.scripting.ScriptUtils;

import java.awt.*;
import java.io.IOException;

/**
 * Created by ADMIN on 4/15/2017.
 */
public class ImageRender {
	private Image image;

	public ImageRender(Image image) {
		this.image = image;
	}

	public ImageRender (String path) throws IOException {
		this(Ultilities.loadImage(path));
	}

	public void render(Graphics graphics, GameRect gameRect){
		graphics.drawImage(image,gameRect.getX(), gameRect.getY(),gameRect.getWidth(),gameRect.getHeight(),null);
	}
}
