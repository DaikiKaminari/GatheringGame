package gatheringgame.server.util;

import java.util.Random;

public class Rand {
	private final Random random;

	public Rand(){
		this.random = new Random();
	}

	public int randomInt(int from, int to) {
		if (from < to)
			return from + random.nextInt(Math.abs(to - from));
		return from - random.nextInt(Math.abs(to - from));
	}
}
