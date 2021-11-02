package gatheringgame.server.util;

import java.util.Random;

public final class Rand {
	private static final Random RANDINSTANCE = new Random();

	private Rand(){}

	public static int randomInt(int from, int to) {
		if (from < to)
			return from + RANDINSTANCE.nextInt(Math.abs(to - from));
		return from - RANDINSTANCE.nextInt(Math.abs(to - from));
	}
}
