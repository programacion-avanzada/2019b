package server;

import shared.Ball;
import shared.BallList;

public class Game {
	private final long NANO_SECONDS_IN_SECOND = 1_000_000_000;
	private final long TICKS_PER_SECOND = 2000;
	private final long NANO_SECONDS_PER_TICK = NANO_SECONDS_IN_SECOND / TICKS_PER_SECOND;

	private long timeStart;

	public void run() {
		timeStart = System.nanoTime();

		// Server can't allow to use Thread.sleep inside the while, because it will stop
		// execution more than 1ms (and up to 15ms in some OS)

		// This method doesn't skip ticks so the game will run slower
		long next_game_tick = timeStart;

		while (true) {
			if (System.nanoTime() > next_game_tick) {
				next_game_tick += NANO_SECONDS_PER_TICK;
				update(1.0 / TICKS_PER_SECOND);
			}
		}

		// This method skip ticks when necessary but has an error of one or more ms per
		// second in optimal conditions
//		long last_game_tick = timeStart;
//		long current_time;
//		long delta;
//
//		while (true) {
//			current_time = System.nanoTime();
//			delta = current_time - last_game_tick;
//			if (delta > NANO_SECONDS_PER_TICK) {
//				update(NANO_SECONDS_IN_SECOND / delta);
//				last_game_tick = current_time;
//			}
//		}

	}

	public void update(double deltaTime) {
		Ball[] balls = BallList.getInstance().getBalls();
		for (Ball ball : balls) {
			ball.move(1.0 / TICKS_PER_SECOND);
		}
	}

	public long getTimeStart() {
		return timeStart;
	}
}
