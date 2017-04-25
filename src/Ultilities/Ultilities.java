package Ultilities;

import Model.GameRect;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by ADMIN on 4/15/2017.
 */
public class Ultilities
{
	public static Image loadImage(String path) throws IOException {
		return ImageIO.read(new File(path));
	}

	public static boolean isHitted (GameRect gameRect1, GameRect gameRect2){
		GameRect gameRectA = gameRect1;
		GameRect gameRectB = gameRect2;
		if(gameRect1.getX()<= gameRect2.getX()){
			gameRectA = gameRect1;
			gameRectB = gameRect2;
		} else{
			gameRectA = gameRect2;
			gameRectB = gameRect1;
		}

		int x = gameRectB.getX() + gameRectB.getWidth() - gameRectA.getX();
		int y = gameRectB.getY() + gameRectB.getHeight() - gameRectA.getY();
		int xA = Math.abs(x);
		int yA = Math.abs(y);
		if((xA < gameRectA.getWidth() + gameRectB.getWidth()) && (yA <gameRectA.getHeight() +gameRectB.getHeight())){
			return true;
		}else {
			return false;
		}
	}
}
