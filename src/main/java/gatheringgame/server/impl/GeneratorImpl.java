package gatheringgame.server.impl;

import gatheringgame.server.Generator;
import gatheringgame.server.Position;
import gatheringgame.server.Resource;
import gatheringgame.server.util.Rand;

public class GeneratorImpl implements Generator {
	Position minPos;
	Position maxPos;

	public GeneratorImpl(Position minPos, Position maxPos) {
		this.minPos = minPos;
		this.maxPos = maxPos;
	}

	@Override
	public Resource getNewRandomResource() {
		Position pos = new PositionImpl(
				Rand.randomInt(minPos.getX(), maxPos.getY()),
				Rand.randomInt(minPos.getY(), maxPos.getY())
		);
		return (new ResourceImpl(Item.randomItem(), pos));
	}
}
