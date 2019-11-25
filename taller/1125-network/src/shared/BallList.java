package shared;

import java.awt.Color;
import java.util.HashMap;

public class BallList {
	private final Color[] colors = { new Color(230, 25, 75), new Color(60, 180, 75), new Color(255, 225, 25),
			new Color(0, 130, 200), new Color(245, 130, 48), new Color(145, 30, 180), new Color(70, 240, 240),
			new Color(240, 50, 230), new Color(210, 245, 60), new Color(250, 190, 190), new Color(0, 128, 128),
			new Color(230, 190, 255), new Color(170, 110, 40), new Color(255, 250, 200), new Color(128, 0, 0),
			new Color(170, 255, 195), new Color(128, 128, 0), new Color(255, 215, 180), new Color(0, 0, 128) };

	private static BallList INSTANCE = null;
	private HashMap<Integer, Ball> balls;

	private BallList() {
		balls = new HashMap<>();
	}

	public static BallList getInstance() {
		if (INSTANCE == null)
			INSTANCE = new BallList();
		return INSTANCE;
	}

	public Ball[] getBalls() {
		return balls.values().toArray(new Ball[0]);
	}

	public HashMap<Integer, Ball> getHashBalls() {
		return balls;
	}

	public Ball getBall(int id) {
		if (balls.get(id) == null) {
			Ball ball = new Ball(0, 9, colors[id % 19]);
			balls.put(id, ball);
		}
		return balls.get(id);
	}

	public void destroyBall(int id) {
		balls.remove(id);
	}
}
