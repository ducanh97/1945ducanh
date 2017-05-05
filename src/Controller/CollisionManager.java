package Controller;

import GAme.*;
import Model.GameRect;
import Ultilities.Ultilities;

import java.util.ArrayList;

/**
 * Created by ADMIN on 4/26/2017.
 */
public class CollisionManager {

	public static final CollisionManager instance = new CollisionManager();
	private ArrayList<Collider> colliders;


	private CollisionManager() {
		colliders = new ArrayList<>();
	}


	public void update() {
				for (int i = 0; i < colliders.size() - 1; i++) {
			for (int j = i + 1; j < colliders.size(); j++) {
				Collider ci = colliders.get(i);
				Collider cj = colliders.get(j);

				GameRect recti = ci.getGameRect();
				GameRect rectj = cj.getGameRect();


				if (recti.intersects(rectj)) {

					if (cj.getClass().equals(ci.getClass())) {

					} else if (cj instanceof Bullet && ((Bullet) cj).getType().contains("plane") && i == 0) {

					} else if ((cj instanceof Bullet && ((Bullet) cj).getType().equals("enemy") && ci instanceof EnemyController) || ((ci instanceof Bullet && ((Bullet) ci).getType().equals("enemy") && cj instanceof EnemyController))) {

					} else{
						ci.onCollide(cj);
						cj.onCollide(ci);
						i--;
						j--;
						break;

					}
				}
			}
		}
	}

	public void add(Collider collider) {
		colliders.add(collider);
	}

	public void remove(Collider collider) {
		colliders.remove(collider);
	}

	}
