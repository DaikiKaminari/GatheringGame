package gatheringgame.server.impl;

import gatheringgame.server.Generator;
import gatheringgame.server.Position;
import gatheringgame.server.Resource;
import gatheringgame.server.util.Rand;

public class GeneratorImpl implements Generator {
	Position minPos;
	Position maxPos;
	Rand rand;

	public GeneratorImpl(Position minPos, Position maxPos) {
		this.minPos = minPos;
		this.maxPos = maxPos;
		this.rand = new Rand();
	}

	public GeneratorImpl(Position minPos, Position maxPos, Rand rand) {
		this.minPos = minPos;
		this.maxPos = maxPos;
		this.rand = rand;
	}

	@Override
	public Resource randomResource() {
		Position pos = new PositionImpl(
				rand.randomInt(minPos.getX(), maxPos.getY()),
				rand.randomInt(minPos.getY(), maxPos.getY())
		);
		return (new ResourceImpl(Item.randomItem(), pos));
	}
}
