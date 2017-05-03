package GAme;

import Model.GameRect;

/**
 * Created by ADMIN on 4/26/2017.
 */
public interface Collider {
	GameRect getGameRect();

	void onCollide(Collider other);

}
