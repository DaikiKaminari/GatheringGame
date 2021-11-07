package gatheringgame.server.impl;

import gatheringgame.server.Generator;
import gatheringgame.server.Position;
import gatheringgame.server.Resource;
import gatheringgame.server.util.Rand;

import java.rmi.RemoteException;

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
	public Resource randomResource() throws RemoteException {
		Position pos = new PositionImpl(
				rand.randomInt((int)minPos.getX(), (int)maxPos.getY()),
				rand.randomInt((int)minPos.getY(), (int)maxPos.getY())
		);
		return (new ResourceImpl(Item.randomItem(), pos));
	}
}
