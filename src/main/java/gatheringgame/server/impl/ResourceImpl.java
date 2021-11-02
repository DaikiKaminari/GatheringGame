package gatheringgame.server.impl;

import gatheringgame.server.Position;
import gatheringgame.server.Resource;

public class ResourceImpl implements Resource {
	private final Item item;
	private final Position position;

	public ResourceImpl(Item item, Position position) {
		this.item = item;
		this.position = position;
	}

	@Override
	public String getName() {
		return item.getName();
	}

	@Override
	public Position getPosition() {
		return position;
	}
}
