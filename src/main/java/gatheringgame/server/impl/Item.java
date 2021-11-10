package gatheringgame.server.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public enum Item implements Serializable {
	SCREW("Screw"),
	GEAR("Gear"),
	BOLT("Bolt");

	private final String name;
	private static final Random rand = new Random();

	public static final List<Item> ITEMS = List.of(values());
	public static final int SIZE = ITEMS.size();

	private Item(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static Item randomItem() {
		return ITEMS.get(rand.nextInt(SIZE));
	}
}
